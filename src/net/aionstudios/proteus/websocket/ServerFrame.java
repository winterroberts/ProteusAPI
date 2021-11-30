package net.aionstudios.proteus.websocket;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import net.aionstudios.proteus.api.event.Callback;
import net.aionstudios.proteus.request.ProteusWebSocketConnection;

public class ServerFrame {
	
	private OpCode opCode;
	private byte[] payload;
	private boolean finished;
	
	private Callback callback;

	protected ServerFrame(OpCode opCode, byte[] payload, boolean finished) {
		this.opCode = opCode;
		this.payload = payload;
		this.finished = finished;
	}
	
	protected ServerFrame(OpCode opCode, byte[] payload, boolean finished, Callback callback) {
		this.opCode = opCode;
		this.payload = payload;
		this.finished = finished;
		this.callback = callback;
	}
	
	public byte getOpByte() {
		return (byte) ((byte) opCode.getValue() | (finished ? 0x80 : 0x00));
	}
	
	public void callback() {
		callback.call();
	}
	
	public boolean isEnd() {
		return finished;
	}
	
	public byte[] getLengthBytes() {
		ByteBuffer bytes;
		if (payload.length > 125) {
			if (payload.length > 65535) {
				bytes = ByteBuffer.allocate(5);
				bytes.put((byte) 127);
				bytes.putLong(payload.length & 0x7FFFFFFFFFFFFFFL);
			} else {
				bytes = ByteBuffer.allocate(3);
				bytes.put((byte) 126);
				bytes.putChar((char) (payload.length & 0xFFFF)); // Char is unsigned 16 (unsigned short).
			}
		} else {
			bytes = ByteBuffer.allocate(1);
			bytes.put((byte) payload.length);
		}
		return bytes.array();
	}
	
	public byte[] getPayload() {
		return payload;
	}
	
	public static ServerFrame closingFrame(ClosingCode code, String message, Callback callback) {
		message = message == null ? "" : message;
		callback = callback == null ? Callback.NULL : callback;
		byte[] pMessage = message.getBytes(StandardCharsets.UTF_8);
		if (pMessage.length > ProteusWebSocketConnection.FRAME_MAX - 2) {
			throw new IllegalArgumentException("Closing message (size " + (pMessage.length + 2) + ") exceeds maximum frame size!");
		}
		ByteBuffer payload = ByteBuffer.allocate(2 + pMessage.length);
		payload.putShort(code.getCode());
		payload.put(pMessage);
		return new ServerFrame(OpCode.CLOSE, payload.array(), true, callback);
	}
	
	public static ServerFrame pingFrame(String message, Callback callback) {
		message = message == null ? "" : message;
		callback = callback == null ? Callback.NULL : callback;
		byte[] payload = message.getBytes(StandardCharsets.UTF_8);
		if (payload.length > 125) {
			throw new IllegalArgumentException("Ping message (size " + payload.length + ") exceeds control frame size!");
		}
		return new ServerFrame(OpCode.PING, payload, true, callback);
	}
	
	public static ServerFrame pongFrame(String message, Callback callback) {
		message = message == null ? "" : message;
		callback = callback == null ? Callback.NULL : callback;
		byte[] payload = message.getBytes(StandardCharsets.UTF_8);
		if (payload.length > 125) {
			throw new IllegalArgumentException("Pong message (size " + payload.length + ") exceeds control frame size!");
		}
		return new ServerFrame(OpCode.PONG, payload, true, callback);
	}
	
}
