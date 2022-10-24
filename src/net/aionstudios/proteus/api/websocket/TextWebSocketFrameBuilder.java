package net.aionstudios.proteus.api.websocket;

import java.nio.charset.StandardCharsets;

public class TextWebSocketFrameBuilder extends WebSocketFrameBuilder {

	protected TextWebSocketFrameBuilder() {
		super(OpCode.TEXT);
	}
	
	/**
	 * Adds the following bytes to the server frame if possible.
	 * 
	 * @param bytes The bytes to be added.
	 * @return True if the bytes did not exceed the length of the buffer, false otherwise.
	 */
	public boolean append(String s) {
		return super.append(s.getBytes(StandardCharsets.UTF_8));
	}
	
}
