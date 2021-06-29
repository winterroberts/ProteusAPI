package net.aionstudios.proteus.configuration;

import javax.net.ssl.SSLContext;

import net.aionstudios.proteus.api.context.ProteusWebSocketContext;

public class WebSocketConfiguration extends EndpointConfiguration {
	
	private ContextController<ProteusWebSocketContext> contextController;
	
	public WebSocketConfiguration(int port, ProteusWebSocketContext... contexts) {
		this(port, null, contexts);
	}
	
	public WebSocketConfiguration(int port, SSLContext sslContext, ProteusWebSocketContext... contexts) {
		super(port, sslContext);
		contextController = ContextController.newInstance(contexts);
	}
	
	@Override
	public ContextController<ProteusWebSocketContext> getContextController() {
		return contextController;
	}

}
