package net.aionstudios.proteus.configuration;

import java.util.HashMap;
import java.util.Map;

import net.aionstudios.proteus.api.context.ProteusHttpContext;
import net.aionstudios.proteus.api.context.ProteusWebSocketContext;
import net.aionstudios.proteus.routing.HttpRoute;
import net.aionstudios.proteus.routing.PathInterpreter;
import net.aionstudios.proteus.routing.WebSocketRoute;

/**
 * A class which points {@link PathInterpreter}s  to respective {@link ProteusHttpContext}/{@link ProteusWebSocketContext}s.
 * 
 * @author Winter Roberts
 *
 */
public class ContextController {

	private Map<PathInterpreter, ProteusHttpContext> httpContexts;
	private Map<PathInterpreter, ProteusWebSocketContext> webSocketContexts;
	
	private ProteusHttpContext defaultHttpContext = null;
	private ProteusWebSocketContext defaultWebSocketContext = null;
	
	/**
	 * @return A new instance of this class.
	 */
	public static ContextController newInstance() {
		return new ContextController();
	}
	
	/**
	 * @return A map of all {@link PathInterpreter}s to their {@link ProteusHttpContext}s.
	 */
	public Map<PathInterpreter, ProteusHttpContext> getHttpContexts() {
		// TODO do something better here.
		return httpContexts;
	}
	
	/**
	 * @return A map of all {@link PathInterpreter}s to their {@link ProteusWebSocketContext}s.
	 */
	public Map<PathInterpreter, ProteusWebSocketContext> getWebSocketContexts() {
		// TODO do something better here.
		return webSocketContexts;
	}
	
	/**
	 * Returns an {@link HttpRoute} for the string path matched by a {@link PathInterpreter} in this context controller.
	 * 
	 * @param path The string path to be comprehended.
	 * @return An {@link HttpRoute} which contains the {@link ProteusHttpContext} and {@link PathComprehension} for this
	 * path string or null if the path could not be comprehended by any {@link PathInterpreter}.
	 */
	public HttpRoute getHttpRoute(String path) {
		for (PathInterpreter it : httpContexts.keySet()) {
			if (it.matches(path)) {
				return new HttpRoute(httpContexts.get(it), it.comprehend(path));
			}
		}
		if (defaultHttpContext != null) {
			return new HttpRoute(defaultHttpContext, null);
		}
		return null;
	}
	
	/**
	 * Returns a {@link WebSocketRoute} for the string path matched by a {@link PathInterpreter} in this context controller.
	 * 
	 * @param path The string path to be comprehended.
	 * @return An {@link WebSocketRoute} which contains the {@link ProteusWebSocketContext} and {@link PathComprehension} for this
	 * path string or null if the path could not be comprehended by any {@link PathInterpreter}.
	 */
	public WebSocketRoute getWebSocketRoute(String path) {
		for (PathInterpreter it : webSocketContexts.keySet()) {
			if (it.matches(path)) {
				return new WebSocketRoute(webSocketContexts.get(it), it.comprehend(path));
			}
		}
		if (defaultWebSocketContext != null) {
			return new WebSocketRoute(defaultWebSocketContext, null);
		}
		return null;
	}
	
	/**
	 * @return The default {@link ProteusHttpContext} for this controller (when no route is found inside the controller), which may be null.
	 */
	public ProteusHttpContext getHttpDefault() {
		return defaultHttpContext;
	}
	
	/**
	 * @return The default {@link ProteusWebSocketContext} for this controller (when no route is found inside the controller), which may be null.
	 */
	public ProteusWebSocketContext getWebSocketDefault() {
		return defaultWebSocketContext;
	}
	
	/**
	 * Sets the default {@link ProteusHttpContext} for this controller.
	 * @param context The new default.
	 */
	public void setHttpDefault(ProteusHttpContext context) {
		this.defaultHttpContext = context;
	}
	
	/**
	 * Sets the default {@link ProteusWebSocketContext} for this controller.
	 * @param context The new default.
	 */
	public void setWebSocketDefault(ProteusWebSocketContext context) {
		this.defaultWebSocketContext = context;
	}
	
	/**
	 * Adds the {@link ProteusHttpContext} to this controller using the {@link PathInterpreter}s
	 * provided by the annotated class {@link ProteusContext}
	 * @param context The added context.
	 */
	public void addHttpContext(ProteusHttpContext context) {
		addHttpContext(context, context.getPaths());
	}
	
	/**
	 * Adds the {@link ProteusWebSocketContext} to this controller using the {@link PathInterpreter}s
	 * provided by the annotated class {@link ProteusContext}
	 * @param context The added context.
	 */
	public void addWebSocketContext(ProteusWebSocketContext context) {
		addWebSocketContext(context, context.getPaths());
	}
	
	/**
	 * Adds the {@link ProteusHttpContext} to this controller using the {@link PathInterpreter}s
	 * provided by this method call.
	 * 
	 * @param context The added context.
	 * @param interpreters The {@link PathInterpreters} that can be used to reach this context.
	 */
	public void addHttpContext(ProteusHttpContext context, PathInterpreter...interpreters) {
		for (PathInterpreter path : interpreters)	{
			httpContexts.put(path, context);
		}
	}
	
	/**
	 * Adds the {@link ProteusWebSocketContext} to this controller using the {@link PathInterpreter}s
	 * provided by this method call.
	 * 
	 * @param context The added context.
	 * @param interpreters The {@link PathInterpreters} that can be used to reach this context.
	 */
	public void addWebSocketContext(ProteusWebSocketContext context, PathInterpreter...interpreters) {
		for (PathInterpreter path : interpreters)	{
			webSocketContexts.put(path, context);
		}
	}
	
	private ContextController() {
		this.httpContexts = new HashMap<>();
		this.webSocketContexts = new HashMap<>();
	}
	
}
