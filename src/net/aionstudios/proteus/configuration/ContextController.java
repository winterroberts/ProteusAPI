package net.aionstudios.proteus.configuration;

import java.util.HashMap;
import java.util.Map;

import net.aionstudios.proteus.api.context.ProteusHttpContext;
import net.aionstudios.proteus.api.context.ProteusWebSocketContext;
import net.aionstudios.proteus.routing.HttpRoute;
import net.aionstudios.proteus.routing.PathInterpreter;
import net.aionstudios.proteus.routing.WebSocketRoute;

public class ContextController {

	private Map<PathInterpreter, ProteusHttpContext> httpContexts;
	private Map<PathInterpreter, ProteusWebSocketContext> webSocketContexts;
	
	private ProteusHttpContext defaultHttpContext = null;
	private ProteusWebSocketContext defaultWebSocketContext = null;
	
	public static ContextController newInstance() {
		return new ContextController();
	}
	
	public Map<PathInterpreter, ProteusHttpContext> getHttpContexts() {
		return httpContexts;
	}
	
	public Map<PathInterpreter, ProteusWebSocketContext> getWebSocketContexts() {
		return webSocketContexts;
	}
	
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
	
	public ProteusHttpContext getHttpDefault() {
		return defaultHttpContext;
	}
	
	public ProteusWebSocketContext getWebSocketDefault() {
		return defaultWebSocketContext;
	}
	
	public void setHttpDefault(ProteusHttpContext context) {
		this.defaultHttpContext = context;
	}
	
	public void setWebSocketDefault(ProteusWebSocketContext context) {
		this.defaultWebSocketContext = context;
	}
	
	public void addHttpContext(ProteusHttpContext context) {
		addHttpContext(context, context.getPaths());
	}
	
	public void addWebSocketContext(ProteusWebSocketContext context) {
		addWebSocketContext(context, context.getPaths());
	}
	
	public void addHttpContext(ProteusHttpContext context, PathInterpreter...interpreters) {
		for (PathInterpreter path : interpreters)	{
			httpContexts.put(path, context);
		}
	}
	
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
