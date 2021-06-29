package net.aionstudios.proteus.configuration;

import javax.net.ssl.SSLContext;

public abstract class EndpointConfiguration {
	
	private int port;
	private SSLContext sslContext;
	private boolean secure;
	
	// Preferably each secure server implementation can use its own JKS
	public EndpointConfiguration(int port, SSLContext sslContext) {
		this.port = port;
		if (sslContext != null ) {
			this.sslContext = sslContext;
			secure = true;
		}
	}
	
	public abstract ContextController<?> getContextController();
	
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
