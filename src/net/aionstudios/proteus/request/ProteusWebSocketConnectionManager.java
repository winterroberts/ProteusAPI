package net.aionstudios.proteus.request;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import net.aionstudios.proteus.websocket.ClosingCode;
import net.aionstudios.proteus.websocket.WebSocketState;

public class ProteusWebSocketConnectionManager {
	
	private static ProteusWebSocketConnectionManager singleton;
	
	private Set<ProteusWebSocketConnection> connections;
	
	private ProteusWebSocketConnectionManager() {
		connections = new HashSet<>();
		singleton = this;
	}
	
	public static ProteusWebSocketConnectionManager getConnectionManager() {
		return singleton == null ? new ProteusWebSocketConnectionManager() : singleton;
	}
	
	public void closeAll() {
		closeAll(true);
	}
	
	public void closeAll(boolean wait) {
		for (ProteusWebSocketConnection conn : connections) {
			if (conn.getState() != WebSocketState.CLOSED && conn.getState() != WebSocketState.CLOSING) {
				conn.close(ClosingCode.GOING_AWAY, "Server is going away");
			}
		}
		while (connections.size() > 0) {
			// wait
		}
	}
	
	protected void removeConnection(ProteusWebSocketConnection conn) {
		if (connections.contains(conn)) {
			connections.remove(conn);
		}
	}
	
	public void startConnection(ProteusWebSocketConnection conn) throws IOException {
		if (!connections.contains(conn)) {
			try {
				conn.handshake();
				connections.add(conn);
			} catch (NoSuchAlgorithmException | IOException e) {
				conn.getClient().close();
			}
		}
	}

}
