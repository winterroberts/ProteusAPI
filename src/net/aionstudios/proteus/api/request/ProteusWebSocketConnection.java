package net.aionstudios.proteus.api.request;

import net.aionstudios.proteus.api.websocket.ClosingCode;
import net.aionstudios.proteus.api.websocket.WebSocketFrame;
import net.aionstudios.proteus.api.websocket.WebSocketState;

/**
 * Manages a client connection to the internal web socket server.
 * 
 * @author Winter Roberts
 *
 */
public interface ProteusWebSocketConnection {
	
	public static int FRAME_MAX = 1048576; // 1 MiB per frame.
	public static int MAX_SIZE = 8 * 1048576;
	
	/**
	 * @return The last timestamp of a successful heartbeat initiated by either client or server.
	 */
	public long getHeartbeat();
	
	/**
	 * @return The current {@link WebSocketState} of this connection.
	 */
	public WebSocketState getState();
	
	/**
	 * Queues reply frams in-order. This may be a blocking operation while waiting on other threads.
	 * Note: Read from this queue is non-blocking.
	 * 
	 * @param frames {@link WebSocketFrame}(s) to be queued (in order) for reply.
	 */
	public void queueFrames(WebSocketFrame... frames);
	
	/**
	 * Enqueues a new normal closing operation on this connection, changing the {@link WebSocketState} to CLOSING.
	 */
	public void close();
	
	/**
	 * Enqueues a new closing operation on this connection, changing the {@link WebSocketState} to CLOSING.
	 * @param code The {@link ClosingCode} to notify the client of the close reason.
	 * @param reason The reason for closing written out (which may be empty but not null).
	 */
	public void close(ClosingCode code, String reason);
	
	/**
	 * Run internal code to start the connection.
	 * @return True if the web socket connection starts successfully, false otherwise.
	 */
	public boolean tryStart();
	
	/**
	 * @return The {@link ProteusWebSocketRequest} (from the initial request) of this connection.
	 */
	public ProteusWebSocketRequest getRequest();

}
