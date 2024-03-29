package net.winrob.proteus.api.request;

import net.winrob.proteus.api.context.ProteusWebSocketContext;
import net.winrob.proteus.header.ProteusHttpHeaders;
import net.winrob.proteus.routing.CompositeRouter;
import net.winrob.proteus.routing.Hostname;
import net.winrob.proteus.routing.PathComprehension;
import net.winrob.proteus.routing.WebSocketRoute;

/**
 * Collects endpoint-relevant request information to pass to {@link ProteusWebSocketContext}s.
 * 
 * @author Winter Roberts
 *
 */
public interface ProteusWebSocketRequest {
	
	/**
	 * @return The {@link Hostname} (which must exist in the endpoint) specified in the request.
	 */
	public Hostname getHostname();

	/**
	 * @return A {@link ParameterMap<String>}, the query parameters (in url) specified in this request, which may be empty.
	 */
	public ParameterMap<String> getUrlParameters();

	/**
	 * @return The {@link RequestBody} generated by this request, which may contain no form or file data.
	 */
	public RequestBody getRequestBody();

	/**
	 * @return A {@link ParameterMap<String>}, the cookies attached to this request.
	 */
	public ParameterMap<String> getCookies();
	
	/**
	 * @return The {@link ProteusHttpHeaders} specified in this request.
	 */
	public ProteusHttpHeaders getHeaders();

	/**
	 * @return The {@link ProteusWebSocketContext} reached by the {@link WebSocketRoute} compiled from the path and {@link CompositeRouter}, or null if no context was reached.
	 */
	public ProteusWebSocketContext getContext();
	
	/**
	 * @return The {@link PathComprehension} with variable and resolution information from the {@link WebSocketRoute} discovered, or null if no route was found.
	 */
	public PathComprehension getPathComprehension();
	
	/**
	 * @return True if the path was accepted by the {@link CompositeRouter} on the specified endpoint, false otherwise.
	 */
	public boolean routed();
	
}
