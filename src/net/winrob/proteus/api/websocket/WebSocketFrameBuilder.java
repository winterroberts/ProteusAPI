package net.winrob.proteus.api.websocket;

import java.nio.ByteBuffer;

import net.winrob.proteus.api.request.ProteusWebSocketConnection;
import net.winrob.proteus.util.StreamUtils;

/**
 * Used to construct server frames from data.
 * 
 * @author Winter Roberts
 *
 */
public class WebSocketFrameBuilder {
	
	private ByteBuffer byteBuffer;
	private int bufferSize;
	
	private OpCode opCode;
	
	private Runnable callback = null;

	protected WebSocketFrameBuilder(OpCode opCode) {
		byteBuffer = ByteBuffer.allocate(0);
		if (opCode == null || opCode.getValue() > 0x3) {
			throw new NullPointerException("OpCode invalid (" + (opCode == null ? "NULL" : opCode.getValue()) + ")");
		}
		this.opCode = opCode;
	}
	
	/**
	 * Creates a new frame builder.
	 * @param opCode The {@link OpCode} of the first frame, which may change if this server frame builder uses continuation frames.
	 * @return A new server frame builder.
	 */
	public static BinaryWebSocketFrameBuilder newBinaryFrameBuilder() {
		return new BinaryWebSocketFrameBuilder();
	}
	
	/**
	 * Creates a new frame builder.
	 * @param opCode The {@link OpCode} of the first frame, which may change if this server frame builder uses continuation frames.
	 * @return A new server frame builder.
	 */
	public static TextWebSocketFrameBuilder newTextFrameBuilder() {
		return new TextWebSocketFrameBuilder();
	}
	
	/**
	 * Adds the following bytes to the server frame if possible.
	 * 
	 * @param bytes The bytes to be added.
	 * @return True if the bytes did not exceed the length of the buffer, false otherwise.
	 */
	protected boolean append(byte... bytes) {
		if (bytes.length + bufferSize <= ProteusWebSocketConnection.MAX_SIZE) {
			byteBuffer = StreamUtils.joinByteArrayToBuffer(byteBuffer.array(), bytes);
			bufferSize += bytes.length;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the callback to be used by the last frame built from this server frame builder.
	 * @param callback The {@link Callback} which may be null.
	 */
	public void setCallback(Runnable callback) {
		this.callback = callback;
	}
	
	/**
	 * Builds the current state of the server frame builder into one or more server frames.
	 * @return The {@link WebSocketFrame1}s encoding the data in this builder.
	 */
	public WebSocketFrame[] build() {
		int maxFrame = ProteusWebSocketConnection.FRAME_MAX;
		boolean divisible = bufferSize % maxFrame == 0;
		byteBuffer.position(0);
		WebSocketFrame[] frames = new WebSocketFrame[(bufferSize / maxFrame) + (!divisible ? 1 : 0)];
		for (int i = 0; i < frames.length; i++) {
			int readLength = (i + 1) == frames.length ? bufferSize % maxFrame : maxFrame;
			byte[] frameBytes = new byte[readLength];
			byteBuffer.get(frameBytes);
			frames[i] = new WebSocketFrame(i == 0 ? opCode : OpCode.CONTINUATION, frameBytes, (i + 1) == frames.length, callback);
		}
		return frames;
	}
	
}
