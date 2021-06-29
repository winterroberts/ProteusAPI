package net.aionstudios.proteus.configuration;

import net.aionstudios.hestia.BinaryNearestMap;
import net.aionstudios.hestia.ComparablePair;
import net.aionstudios.proteus.api.context.ProteusHttpContext;
import net.aionstudios.proteus.api.context.ProteusWebSocketContext;
import net.aionstudios.proteus.api.util.URLUtils;

public class ContextController<T> {

	private BinaryNearestMap<String, T> contexts;
	
	private T defaultContext = null;
	
	public static ContextController<ProteusHttpContext> newInstance(ProteusHttpContext... contexts) {
		ContextController<ProteusHttpContext> controller = new ContextController<>();
		for (ProteusHttpContext c : contexts) {
			for (String path : c.getPaths())	{
				if (URLUtils.isValidPathSegment(path)) {
					controller.contexts.put(path, c);
				}
			}
		}
		return controller;
	}
	
	public static ContextController<ProteusWebSocketContext> newInstance(ProteusWebSocketContext... contexts) {
		ContextController<ProteusWebSocketContext> controller = new ContextController<>();
		for (ProteusWebSocketContext c : contexts) {
			for (String path : c.getPaths())	{
				if (URLUtils.isValidPathSegment(path)) {
					controller.contexts.put(path, c);
				}
			}
		}
		return controller;
	}
	
	public BinaryNearestMap<String, T> getContexts() {
		return contexts;
	}
	
	public T getPathContext(String path) {
		ComparablePair<String, T> context = contexts.get(contexts.nearestMatch(path));
		if (path.startsWith(context.getKey())) {
			return context.getValue();
		}
		return null;
	}
	
	public T getDefault() {
		return defaultContext;
	}
	
	public void setDefault(T context) {
		this.defaultContext = context;
	}
	
	private ContextController() {
		this.contexts = new BinaryNearestMap<>();
	}
	
}
