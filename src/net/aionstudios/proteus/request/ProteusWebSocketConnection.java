package net.aionstudios.proteus.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import net.aionstudios.proteus.api.context.ProteusWebSocketContext;
import net.aionstudios.proteus.api.event.Callback;
import net.aionstudios.proteus.api.util.SecurityUtils;
import net.aionstudios.proteus.header.ProteusHeaderBuilder;
import net.aionstudios.proteus.header.ProteusHttpHeaders;
import net.aionstudios.proteus.websocket.ClosingCode;
import net.aionstudios.proteus.websocket.DataType;
import net.aionstudios.proteus.websocket.OpCode;
import net.aionstudios.proteus.websocket.ServerFrame;
import net.aionstudios.proteus.websocket.WebSocketState;

public class ProteusWebSocketConnection {
	
	public static int FRAME_MAX = 1048576; // 1 MiB per frame.
	public static int MAX_SIZE = 8 * 1048576; // 
	
	private Socket client;
	private InputStream inputStream;
	private OutputStream outputStream;
	
	private ProteusWebSocketContext context;
	private ProteusHttpHeaders headers;
	
	private String path;
	private String host;
	
	private WebSocketState state;
	private WebSocketBuffer buffer;
	
	private BlockingDeque<ServerFrame> replyQueue;
	
	private String lastPing;
	private boolean heartbeatWaiting;
	private long heartbeat;
	
	public ProteusWebSocketConnection(Socket client, ProteusWebSocketContext context, String path, String host, ProteusHttpHeaders headers) throws IOException {
		this.client = client;
		this.inputStream = client.getInputStream();
		this.outputStream = client.getOutputStream();
		this.context = context;
		this.path = path;
		this.host = host;
		this.headers = headers;
		replyQueue = new LinkedBlockingDeque<>();
		state = WebSocketState.CONNECTING;
		heartbeat = 0;
	}
	
	protected void handshake() throws NoSuchAlgorithmException, IOException {
		if (state == WebSocketState.CONNECTING) {
			String webSocketKey = headers.getHeader("Sec-WebSocket-Key").getFirst().getValue();
			String keyResponse = Base64.getEncoder().encodeToString(
					MessageDigest.getInstance("SHA-1").digest(
							(webSocketKey + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes("UTF-8")));
			outputStream.write(("HTTP/1.1 101 Switching Protocols\r\n").getBytes());
			ProteusHeaderBuilder headerBuilder = new ProteusHeaderBuilder();
			headerBuilder.putHeader("Connection", "Upgrade");
			headerBuilder.putHeader("Upgrade", "websocket");
			headerBuilder.putHeader("Sec-WebSocket-Accept", keyResponse);
	        for (String header : headerBuilder.toList()) {
	        	outputStream.write((header + "\r\n").getBytes());
	        }
	        outputStream.write("\r\n".getBytes());
	        outputStream.flush();
	        heartbeat = System.currentTimeMillis();
			state = WebSocketState.OPEN;
			Thread reply = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						writeLoop();
						client.close();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			});
			context.onOpen(this);
			reply.start();
			readLoop();
		}
	}
	
	private void writeLoop() throws InterruptedException, IOException {
		while (state != WebSocketState.CLOSED) {
			ServerFrame replyFrame = replyQueue.take();
			outputStream.write(replyFrame.getOpByte());
			outputStream.write(replyFrame.getLengthBytes());
			outputStream.write(replyFrame.getPayload());
			
			if (replyFrame.isEnd()) {
				replyFrame.callback();
				long diff = System.currentTimeMillis() - heartbeat;
				if (diff > 60000) {
					closeInternal(true);
				} else if (diff > 20000 && !heartbeatWaiting) {
					lastPing = SecurityUtils.genToken(16);
					heartbeatWaiting = true;
					replyQueue.offerFirst(ServerFrame.pingFrame(lastPing, Callback.NULL));
				}
			}
		}
	}
	
	private void readLoop() throws IOException {
		while (state != WebSocketState.CLOSED) {
			// Start instruction read
			byte startByte = (byte) inputStream.read(); // Blocking IO
			byte opCode = (byte) (startByte & 0x0F);
			boolean fin = ((startByte >> 7) & 0x01) == 1;
			byte secondByte = (byte) inputStream.read();
			boolean masked = (((secondByte >> 7) & 0x01) > 0);
			long readCtrl = (secondByte & 0x7F);
			if (!masked) {
				close(ClosingCode.PROTOCOL_ERROR, "Client indicated message not masked!");
				return;
			}
			// read size
			if (readCtrl == 126) {
				ByteBuffer b = ByteBuffer.allocate(Integer.BYTES);
				b.put(new byte[] { 0, 0 });
				b.put(inputStream.readNBytes(2));
				b.rewind();
				readCtrl = b.getInt();
			} else if (readCtrl == 127) {
				ByteBuffer b = ByteBuffer.allocate(Long.BYTES);
				b.put(inputStream.readNBytes(8));
				b.rewind();
				readCtrl = b.getLong() & 0x7FFFFFFFFFFFFFFFL;
			}
			if (readCtrl > FRAME_MAX || readCtrl < 0) {
				close(ClosingCode.TOO_BIG, "Payload size not supported (too large)!");
				return;
			}
			int read = (int) readCtrl; // We know that this will never be bigger than Integer.MAX_VALUE, so this is okay.
			// handle opcode
			if (!opHandler(OpCode.forValue(opCode), read)) {
				return;
			}
			if (fin) {
				WebSocketBuffer finished = buffer;
				buffer = null;
				try {
					context.onMessage(this, finished);
				} catch (Exception e) {
					context.onError(this, e);
				}
			}
		}
	}
	
	private boolean opHandler(OpCode opCode, int read) throws IOException {
		DataType dt = DataType.BINARY;
		switch(opCode) {
		case TEXT:
			dt = DataType.TEXT;
		case BINARY:
			if (buffer != null) {
				close(ClosingCode.PROTOCOL_ERROR, "Previous buffer not completed!");
				return false;
			} else {
				buffer = new WebSocketBuffer(dt);
				buffer.put(readFrame(read));
			}
			break;
		case CONTINUATION:
			if (buffer == null) {
				close(ClosingCode.PROTOCOL_ERROR, "No existing buffer!");
				return false;
			} else {
				buffer.put(readFrame(read));
			}
			break;
		case CLOSE:
			ProteusWebSocketConnection connection = this;
			if (state == WebSocketState.CLOSING) {
				connection.closeInternal(false);
			} else {
				close(ClosingCode.NORMAL, "", new Callback() {
	
					@Override
					public void call() {
						try {
							connection.closeInternal(false);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
				});
			}
			break;
		case PING:
			if (read > 125) {
				close(ClosingCode.PROTOCOL_ERROR, "Control frame size (125) exceeded in ping!");
				return false;
			} else {
				replyQueue.add(ServerFrame.pongFrame(new String(readFrame(read), StandardCharsets.UTF_8), new Callback() {

					@Override
					public void call() {
						heartbeatWaiting = false;
						heartbeat = System.currentTimeMillis();
					}
					
				}));
			}
			break;
		case PONG:
			if (read > 125) {
				close(ClosingCode.PROTOCOL_ERROR, "Control frame size (125) exceeded in pong!");
				return false;
			} else {
				String pong = new String(readFrame(read), StandardCharsets.UTF_8);
				if (pong.equals(lastPing)) {
					heartbeatWaiting = false;
					heartbeat = System.currentTimeMillis();
				}
			}
			break;
		default:
			throw new NullPointerException("OpCode invalid (NULL)");
		}
		return true;
	}
	
	private byte[] readFrame(int read) throws IOException {
		byte[] mask = inputStream.readNBytes(4);
		byte[] bytes = inputStream.readNBytes(read);
		for (int i = 0; i < read; i++) {
			bytes[i] = (byte) (bytes[i] ^ mask[i % 4]);
		}
		return bytes;
	}
	
	public long getHeartbeat() {
		return heartbeat;
	}
	
	public WebSocketState getState() {
		return state;
	}
	
	private void queueFrames(ServerFrame[] frames) {
		for (ServerFrame frame : frames) {
			replyQueue.add(frame);
		}
	}
	
	public void close() throws IOException {
		close(ClosingCode.NORMAL, "");
	}
	
	public void close(ClosingCode code, String reason) {
		if (state == WebSocketState.OPEN) {
			ProteusWebSocketConnection connection = this;
			close(code, reason, new Callback() {

				@Override
				public void call() {
					new Timer().schedule(new TimerTask() {

						@Override
						public void run() {
							try {
								connection.closeInternal(true);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						
					}, 20000);
				}
				
			});
		}
	}
	
	private void closeInternal(boolean force) throws IOException {
		synchronized (this) {
			if (force && state != WebSocketState.CLOSED) state = WebSocketState.CLOSING;
			if (state == WebSocketState.CLOSING) {
				state = WebSocketState.CLOSED;
				replyQueue.clear();
				buffer = null;
				client.close();
				ProteusWebSocketConnectionManager.getConnectionManager().removeConnection(this);
				context.onClose(this);
			}
		}
	}
	
	private void close(ClosingCode code, String reason, Callback callback) {
		state = WebSocketState.CLOSING;
		replyQueue.offerFirst(ServerFrame.closingFrame(code, reason, callback));
	}
	
	protected Socket getClient() {
		return client;
	}

}
