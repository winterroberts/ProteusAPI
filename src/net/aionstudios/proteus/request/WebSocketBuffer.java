package net.aionstudios.proteus.request;

import java.nio.ByteBuffer;

import net.aionstudios.proteus.api.util.StreamUtils;
import net.aionstudios.proteus.websocket.DataType;

public class WebSocketBuffer {
	
	private ByteBuffer buffer;
	private int bufferSize;
	private DataType dataType;
	
	public WebSocketBuffer(DataType dataType) {
		buffer = ByteBuffer.allocate(0);
		bufferSize = 0;
		this.dataType = dataType;
	}
	
	public boolean put(byte[] data) {
		if (bufferSize + data.length <= ProteusWebSocketConnection.MAX_SIZE) {
			buffer = StreamUtils.joinByteArrayToBuffer(buffer.array(), data);
			bufferSize += data.length;
			return true;
		} else {
			return false;
		}
	}
	
	public DataType getDataType() {
		return dataType;
	}
	
	public int getBufferSize() {
		return bufferSize;
	}
	
	public byte[] getData() {
		return buffer.array();
	}
	
}
