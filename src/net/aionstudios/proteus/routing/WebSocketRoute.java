package net.aionstudios.proteus.routing;

import net.aionstudios.proteus.api.context.ProteusWebSocketContext;

public class WebSocketRoute {
	
	private ProteusWebSocketContext context;
	private PathComprehension pathComprehension;
	
	public WebSocketRoute(ProteusWebSocketContext context, PathComprehension pathComprehension) {
		this.context = context;
		this.pathComprehension = pathComprehension;
	}
	
	public ProteusWebSocketContext getContext() {
		return context;
	}
	
	public PathComprehension getPathComprehension() {
		return pathComprehension;
	}

}
