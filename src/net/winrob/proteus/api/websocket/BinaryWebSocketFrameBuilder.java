package net.winrob.proteus.api.websocket;

public class BinaryWebSocketFrameBuilder extends WebSocketFrameBuilder {

	protected BinaryWebSocketFrameBuilder() {
		super(OpCode.BINARY);
	}
	
	/**
	 * Adds the following bytes to the server frame if possible.
	 * 
	 * @param bytes The bytes to be added.
	 * @return True if the bytes did not exceed the length of the buffer, false otherwise.
	 */
	public boolean append(byte... bytes) {
		return super.append(bytes);
	}
	
}
