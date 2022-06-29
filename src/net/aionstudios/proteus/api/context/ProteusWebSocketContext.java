package net.aionstudios.proteus.api.context;

import net.aionstudios.proteus.api.request.ProteusWebSocketConnection;
import net.aionstudios.proteus.api.request.WebSocketBuffer;
import net.aionstudios.proteus.routing.PathInterpreter;

@ProteusContext(path="/")
/**
 * An abstraction which accepts traffic for an endpoint (as defined by a {@link Router} and its {@link PathInterpreter}s).
 * 
 * @author Winter Roberts
 *
 */
public abstract class ProteusWebSocketContext {
	
	private ProteusContext useContext;
	private PathInterpreter[] paths;
	
	/**
	 * @return The list of valid path interpretations that accept traffic for this context.
	 */
	public final PathInterpreter[] getPaths() {
		getContext();
		return paths;
	}
	
	private final ProteusContext getContext() {
		if (useContext==null) {
			ProteusContext pc = this.getClass().getAnnotation(ProteusContext.class);
			ProteusContext pct = this.getClass().getAnnotatedSuperclass().getAnnotation(ProteusContext.class);
			if (pct != null && pct.preserveType()) {
				paths = new PathInterpreter[pct.path().length + pc.path().length];
				for (int i = 0; i < pct.path().length; i++) {
					paths[i] = new PathInterpreter(pct.path()[i]);
				}
				for (int i = 0; i < pc.path().length; i++) {
					paths[i+pct.path().length] = new PathInterpreter(pc.path()[i]);
				}
			} else if (pct != null) {
				paths = new PathInterpreter[pct.path().length];
				String[] pctp = pct.path();
				for (int i = 0; i < pctp.length; i++) {
					paths[i] = new PathInterpreter(pctp[i]);
				}
			} else if (pc != null) {
				paths = new PathInterpreter[pc.path().length];
				String[] pcp = pc.path();
				for (int i = 0; i < pcp.length; i++) {
					paths[i] = new PathInterpreter(pcp[i]);
				}
			} else {
				paths = null;
			}
			useContext = pct != null ? pct : pc;
		}
		return useContext;
	}
	
	/**
	 * Determines if any {@link PathInterpreter} associated with this context can build a
	 * comprehension of the given path.
	 * 
	 * @param path The path string (likely from a request)
	 * @return True if this context accepts traffic to the endpoint, false otherwise.
	 */
	public final boolean pathMatch(String path) {
		getContext();
		for (PathInterpreter it : paths) {
			if (it.matches(path)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Called when a connection to this context is opened (after handshake).
	 * 
	 * @param connection The {@link ProteusWebSocketConnection} object, which contains information about the connection
	 * and controls message transmission.
	 */
	public abstract void onOpen(ProteusWebSocketConnection connection);
	
	/**
	 * Called when a message is received by the client on this connection.
	 * 
	 * @param connection The {@link ProteusWebSocketConnection} object, which contains information about the connection
	 * and controls message transmission.
	 * @param message The {@link WebSocketBuffer} containing the message, which may be encoded in a number of ways.
	 */
	public abstract void onMessage(ProteusWebSocketConnection connection, WebSocketBuffer message);
	
	/**
	 * Called after either the client or server confirms a request to close (after both have sent a closing frame).
	 * 
	 * @param connection The {@link ProteusWebSocketConnection} object, which contains information about the connection
	 * and controls message transmission.
	 */
	public abstract void onClose(ProteusWebSocketConnection connection);
	
	/**
	 * Called when an unrecovered error causes a client or server transmission to fail.
	 * 
	 * @param connection The {@link ProteusWebSocketConnection} object, which contains information about the connection
	 * and controls message transmission.
	 * @param throwable The throwable {@link Exception} that caused this error.
	 */
	public abstract void onError(ProteusWebSocketConnection connection, Throwable throwable);

}
