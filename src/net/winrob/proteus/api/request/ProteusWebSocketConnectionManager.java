package net.winrob.proteus.api.request;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import net.winrob.proteus.api.websocket.ClosingCode;
import net.winrob.proteus.api.websocket.WebSocketState;

/**
 * Manages active {@link ProteusWebSocketConnection}s
 * 
 * @author Winter Roberts
 *
 */
public class ProteusWebSocketConnectionManager {
	
	private static ProteusWebSocketConnectionManager singleton;
	
	private Set<ProteusWebSocketConnection> connections;
	
	private ProteusWebSocketConnectionManager() {
		connections = new HashSet<>();
		singleton = this;
	}
	
	/**
	 * @return A singleton connection manager.
	 */
	public static ProteusWebSocketConnectionManager getConnectionManager() {
		return singleton == null ? new ProteusWebSocketConnectionManager() : singleton;
	}
	
	/**
	 * Begins (graceful if possible) closing operations on all active connections.
	 */
	public void closeAll() {
		closeAll(true);
	}
	
	/**
	 * A possibly blocking close operation. It is recommended that blocking closes be used in any case that the server is shutting down immediately after.
	 * @params wait Whether or not to block activity on the current thread while waiting for all exisitng connections to close.
	 */
	public void closeAll(boolean wait) {
		for (ProteusWebSocketConnection conn : connections) {
			if (conn.getState() != WebSocketState.CLOSED && conn.getState() != WebSocketState.CLOSING) {
				conn.close(ClosingCode.GOING_AWAY, "Server is going away");
			}
		}
		while (connections.size() > 0 && wait) {
			// wait
		}
	}
	
	protected void addConnection(ProteusWebSocketConnection conn) {
		if (!connections.contains(conn)) {
			connections.add(conn);
		}
	}
	
	protected void removeConnection(ProteusWebSocketConnection conn) {
		if (connections.contains(conn)) {
			connections.remove(conn);
		}
	}
	
	/**
	 * Starts a new connection, registering it to this connection manager.
	 * 
	 * @param conn The {@link ProteusWebSocketConnection} to be started.
	 * @throws IOException If the client-server handshake fails.
	 */
	public void startConnection(ProteusWebSocketConnection conn) throws IOException {
		if (!connections.contains(conn)) {
			conn.tryStart();
		}
	}

}
