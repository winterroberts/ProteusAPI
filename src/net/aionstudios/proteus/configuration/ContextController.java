package net.aionstudios.proteus.configuration;

import net.aionstudios.hestia.BinaryNearestMap;
import net.aionstudios.hestia.ComparablePair;
import net.aionstudios.proteus.api.context.ProteusContext;
import net.aionstudios.proteus.api.context.ProteusHttpContext;
import net.aionstudios.proteus.api.context.ProteusWebSocketContext;
import net.aionstudios.proteus.api.util.URLUtils;

public class ContextController {

	private BinaryNearestMap<String, ProteusHttpContext> httpContexts;
	private BinaryNearestMap<String, ProteusWebSocketContext> webSocketContexts;
	
	private ProteusHttpContext defaultHttpContext = null;
	private ProteusWebSocketContext defaultWebSocketContext = null;
	
	public static ContextController newInstance() {
		return new ContextController();
	}
	
	public BinaryNearestMap<String, ProteusHttpContext> getHttpContexts() {
		return httpContexts;
	}
	
	public BinaryNearestMap<String, ProteusWebSocketContext> getWebSocketContexts() {
		return webSocketContexts;
	}
	
	public ProteusHttpContext getHttpContext(String path) {
		ComparablePair<String, ProteusHttpContext> context = httpContexts.get(httpContexts.nearestMatch(path));
		if (context != null && path.startsWith(context.getKey())) {
			return context.getValue();
		}
		return null;
	}
	
	public ProteusWebSocketContext getWebSocketContext(String path) {
		ComparablePair<String, ProteusWebSocketContext> context = webSocketContexts.get(webSocketContexts.nearestMatch(path));
		if (context != null && path.startsWith(context.getKey())) {
			return context.getValue();
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
	
	public void setHttpContext(ProteusHttpContext context) {
		for (String path : context.getPaths())	{
			if (URLUtils.isValidPathSegment(path)) {
				httpContexts.put(path, context);
			}
		}
	}
	
	public void setWebSocketContext(ProteusWebSocketContext context) {
		for (String path : context.getPaths())	{
			if (URLUtils.isValidPathSegment(path)) {
				webSocketContexts.put(path, context);
			}
		}
	}
	
	private ContextController() {
		this.httpContexts = new BinaryNearestMap<>();
		this.webSocketContexts = new BinaryNearestMap<>();
	}
	
}
