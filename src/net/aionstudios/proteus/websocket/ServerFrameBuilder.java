package net.aionstudios.proteus.websocket;

import java.nio.ByteBuffer;

import net.aionstudios.proteus.api.event.Callback;
import net.aionstudios.proteus.api.util.StreamUtils;
import net.aionstudios.proteus.request.ProteusWebSocketConnection;

public class ServerFrameBuilder {
	
	private ByteBuffer byteBuffer;
	private int bufferSize;
	
	private OpCode opCode;
	
	private Callback callback = null;

	private ServerFrameBuilder(OpCode opCode) {
		byteBuffer = ByteBuffer.allocate(0);
		if (opCode == null || opCode.getValue() < 0x3) {
			throw new NullPointerException("OpCode invalid (" + (opCode == null ? "NULL" : opCode.getValue()) + ")");
		}
		this.opCode = opCode;
	}
	
	public static ServerFrameBuilder newFrameBuilder(OpCode opCode) {
		return new ServerFrameBuilder(opCode);
	}
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	public boolean addBytes(byte[] bytes) {
		if (bytes.length + bufferSize <= ProteusWebSocketConnection.MAX_SIZE) {
			byteBuffer = StreamUtils.joinByteArrayToBuffer(byteBuffer.array(), bytes);
			bufferSize += bytes.length;
			return true;
		}
		return false;
	}
	
	public ServerFrame[] build() {
		int maxSize = ProteusWebSocketConnection.MAX_SIZE;
		int maxFrame = ProteusWebSocketConnection.FRAME_MAX;
		boolean divisible = maxSize % maxFrame == 0;
		ServerFrame[] frames = new ServerFrame[(maxSize / maxFrame) + (!divisible ? 1 : 0)];
		for (int i = 0; i < frames.length; i++) {
			int readLength = (i + 1) == frames.length ? bufferSize % maxFrame : maxFrame;
			byte[] frameBytes = new byte[readLength];
			byteBuffer.get(frameBytes, i * maxFrame, readLength);
			frames[i] = new ServerFrame(opCode, frameBytes, (i + 1) == frames.length, callback);
		}
		return frames;
	}
	
}
