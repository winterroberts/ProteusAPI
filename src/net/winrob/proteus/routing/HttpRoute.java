package net.winrob.proteus.routing;

import net.winrob.proteus.api.context.ProteusHttpContext;

/**
 * A route derived from the path componeent of a valid HTTP request.
 * 
 * @author Winter Roberts
 *
 */
public class HttpRoute {
	
	private ProteusHttpContext context;
	private PathComprehension pathComprehension;
	
	/**
	 * Creates a new completed HttpRoute.
	 * 
	 * @param context The {@link ProteusHttpContext} named by this route.
	 * @param pathComprehension The {@link PathComprehension} of the request path and interpreter.
	 */
	public HttpRoute(ProteusHttpContext context, PathComprehension pathComprehension) {
		this.context = context;
		this.pathComprehension = pathComprehension;
	}
	
	/**
	 * @return The {@link ProteusHttpContext} reached by this route.
	 */
	public ProteusHttpContext getContext() {
		return context;
	}
	
	/**
	 * @return The {@link PathComprehension} of the request path and interpreter.
	 */
	public PathComprehension getPathComprehension() {
		return pathComprehension;
	}

}
