package net.aionstudios.proteus.routing;

import net.aionstudios.proteus.api.context.ProteusWebSocketContext;

/**
 * A route derived from the path componeent of a valid web socket request.
 * 
 * @author Winter Roberts
 *
 */
public class WebSocketRoute {
	
	private ProteusWebSocketContext context;
	private PathComprehension pathComprehension;
	
	/**
	 * Creates a new completed WebSocketRoute.
	 * 
	 * @param context The {@link ProteusWebSocketContext} named by this route.
	 * @param pathComprehension The {@link PathComprehension} of the request path and interpreter.
	 */
	public WebSocketRoute(ProteusWebSocketContext context, PathComprehension pathComprehension) {
		this.context = context;
		this.pathComprehension = pathComprehension;
	}
	
	/**
	 * @return The {@link ProteusWebSocketContext} reached by this route.
	 */
	public ProteusWebSocketContext getContext() {
		return context;
	}
	
	/**
	 * @return The {@link PathComprehension} of the request path and interpreter.
	 */
	public PathComprehension getPathComprehension() {
		return pathComprehension;
	}

}
