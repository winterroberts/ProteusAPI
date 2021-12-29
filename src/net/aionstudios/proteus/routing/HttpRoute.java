package net.aionstudios.proteus.routing;

import net.aionstudios.proteus.api.context.ProteusHttpContext;

public class HttpRoute {
	
	private ProteusHttpContext context;
	private PathComprehension pathComprehension;
	
	public HttpRoute(ProteusHttpContext context, PathComprehension pathComprehension) {
		this.context = context;
		this.pathComprehension = pathComprehension;
	}
	
	public ProteusHttpContext getContext() {
		return context;
	}
	
	public PathComprehension getPathComprehension() {
		return pathComprehension;
	}

}
