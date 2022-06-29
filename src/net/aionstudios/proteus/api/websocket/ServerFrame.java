package net.aionstudios.proteus.api.websocket;

public interface ServerFrame {
	
	/**
	 * @return The byte indicating the op code to be transmitted.
	 */
	public byte getOpByte();
	
	/**
	 * Calls the callback function (after this frame is sent).
	 */
	public void callback();
	
	/**
	 * @return True if this is a single frame or the last frame in a continuation, false otherwise.
	 */
	public boolean isEnd();
	
	/**
	 * @return The encoded length of the message.
	 */
	public byte[] getLengthBytes();
	
	/**
	 * @return The payload of this frame.
	 */
	public byte[] getPayload();

}
