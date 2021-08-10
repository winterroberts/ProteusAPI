package net.aionstudios.proteus.configuration;

import javax.net.ssl.SSLContext;

import net.aionstudios.proteus.api.context.ProteusWebSocketContext;

public class WebSocketLoopbackConfiguration extends WebSocketConfiguration {
	
	public WebSocketLoopbackConfiguration(int port, ProteusWebSocketContext... contexts) {
		this(port, null, contexts);
	}
	
	public WebSocketLoopbackConfiguration(int port, SSLContext sslContext, ProteusWebSocketContext... contexts) {
		super(port, sslContext, contexts);
	}

}
