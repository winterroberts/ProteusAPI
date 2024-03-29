package net.winrob.proteus.api.request;

import net.winrob.commons.saon.EventDispatcher;
import net.winrob.proteus.api.context.ProteusHttpContext;
import net.winrob.proteus.header.ProteusHttpHeaders;
import net.winrob.proteus.routing.CompositeRouter;
import net.winrob.proteus.routing.Hostname;
import net.winrob.proteus.routing.HttpRoute;
import net.winrob.proteus.routing.PathComprehension;

/**
 * Collects endpoint-relevant request information to pass to {@link ProteusHTTPContext}s.
 * 
 * @author Winter Roberts
 *
 */
public interface ProteusHttpRequest {

	/**
	 * @return The HTTP method specified in the request.
	 */
	public String getMethod();
	
	/**
	 * @return The HTTP version specified in the request.
	 */
	public String getHttpVersion();
	
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
	 * @return The address of the upstream client.
	 */
	public String getRemoteAddress();
	
	/**
	 * @return The {@link ProteusHttpContext} reached by the {@link HttpRoute} compiled from the path and {@link CompositeRouter}, or null if no context was reached.
	 */
	public ProteusHttpContext getContext();

	/**
	 * @return The {@link PathComprehension} with variable and resolution information from the {@link HttpRoute} discovered, or null if no route was found.
	 */
	public PathComprehension getPathComprehension();
	
	public String getPath();
	
	public EventDispatcher getEventDispatcher();
	
	/**
	 * @return True if the path was accepted by the {@link CompositeRouter} on the specified endpoint, false otherwise.
	 */
	public boolean routed();
	
}
