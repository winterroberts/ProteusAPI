package net.winrob.proteus.api.request;

import net.winrob.proteus.api.websocket.DataType;

/**
 * Buffers web socket data from one start (and finish) frame which may consist of 0 or more continuation frames.
 * 
 * @author Winter Roberts
 *
 */
public interface WebSocketBuffer {
	
	/**
	 * Adds bytes to the buffer.
	 * @param data The bytes to be added to the buffer.
	 * @return True if the data could be added, false otherwise (buffer limit exceeded).
	 */
	public boolean put(byte[] data);
	
	/**
	 * @return The {@link DataType} of this buffer.
	 */
	public DataType getDataType();
	
	/**
	 * @return The maximum buffer size (in bytes)
	 */
	public int getBufferSize();
	
	/**
	 * @return The data in this buffer.
	 */
	public byte[] getData();
	
}
