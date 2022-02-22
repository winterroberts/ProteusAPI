package net.aionstudios.proteus.request;

import java.nio.ByteBuffer;

import net.aionstudios.proteus.api.util.StreamUtils;
import net.aionstudios.proteus.websocket.DataType;

/**
 * Buffers web socket data from one start (and finish) frame which may consist of 0 or more continuation frames.
 * 
 * @author Winter Roberts
 *
 */
public class WebSocketBuffer {
	
	private ByteBuffer buffer;
	private int bufferSize;
	private DataType dataType;
	
	/**
	 * Creates a new WebSocketBuffer of the specified data type.
	 * @param dataType The {@link DataType} of this WebSocketBuffer.
	 */
	public WebSocketBuffer(DataType dataType) {
		buffer = ByteBuffer.allocate(0);
		bufferSize = 0;
		this.dataType = dataType;
	}
	
	/**
	 * Adds bytes to the buffer.
	 * @param data The bytes to be added to the buffer.
	 * @return True if the data could be added, false otherwise (buffer limit exceeded).
	 */
	public boolean put(byte[] data) {
		if (bufferSize + data.length <= ProteusWebSocketConnection.MAX_SIZE) {
			buffer = StreamUtils.joinByteArrayToBuffer(buffer.array(), data);
			bufferSize += data.length;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @return The {@link DataType} of this buffer.
	 */
	public DataType getDataType() {
		return dataType;
	}
	
	/**
	 * @return The maximum buffer size (in bytes)
	 */
	public int getBufferSize() {
		return bufferSize;
	}
	
	/**
	 * @return The data in this buffer.
	 */
	public byte[] getData() {
		return buffer.array();
	}
	
}
