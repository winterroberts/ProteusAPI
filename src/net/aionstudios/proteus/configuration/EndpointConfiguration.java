package net.aionstudios.proteus.configuration;

import javax.net.ssl.SSLContext;

public class EndpointConfiguration {
	
	private EndpointType type;
	private int port;
	private SSLContext sslContext;
	private boolean secure;
	
	private ContextController controller;
	
	// Preferably each secure server implementation can use its own JKS
	public EndpointConfiguration(EndpointType type, int port) {
		this(type, port, null);
	}
	
	public EndpointConfiguration(EndpointType type, int port, SSLContext sslContext) {
		this.port = port;
		this.type = type;
		if (sslContext != null ) {
			this.sslContext = sslContext;
			secure = true;
		}
		controller = ContextController.newInstance();
	}
	
	public ContextController getContextController() {
		return controller;
	}
	
	public boolean isHttp() {
		return type.isHttp();
	}
	
	public boolean isWebSocket() {
		return type.isWebSocket();
	}
	
	public int getPort() {
		return port;
	}
	
	public SSLContext getSslContext() {
		return sslContext;
	}
	
	public boolean isSecure() {
		return secure;
	}

}
