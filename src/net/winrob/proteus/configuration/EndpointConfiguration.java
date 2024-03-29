package net.winrob.proteus.configuration;

import javax.net.ssl.SSLContext;

/**
 * Used to configure {@link ProteusServer} resources, allocating routes and HTTP/1.1 and/or WebSocket ports.
 * 
 * @author Winter Roberts
 *
 */
public class EndpointConfiguration {
	
	private EndpointType type;
	private int port;
	
	private ContextController controller;
	
	/**
	 * Creates a new unsecured endpoint configuration.
	 * 
	 * @param type The {@link EndpointType} of this endpoint, which indicates HTTP, WebSocket, or both.
	 * @param port The port this endpoint should open at.
	 */
	public EndpointConfiguration(EndpointType type, int port) {
		this(type, ContextController.newInstance(), port);
	}
	
	public EndpointConfiguration(EndpointType type, ContextController ctxController, int port) {
		this.port = port;
		this.type = type;
		controller = ctxController;
	}
	
	/**
	 * @return The {@link EndpointType} of this endpoint.
	 */
	public EndpointType getType() {
		return type;
	}
	
	/**
	 * @return The {@link ContextController} belonging to this endpoint.
	 */
	public ContextController getContextController() {
		return controller;
	}
	
	/**
	 * @return True if the {@link EndpointType} used to instantiate this endpoint indicated HTTP server is enabled, false otherwise.
	 */
	public boolean isHttp() {
		return type.isHttp();
	}
	
	/**
	 * @return True if the {@link EndpointType} used to instantiate this endpoint indicated WebSocket server is enabled, false otherwise.
	 */
	public boolean isWebSocket() {
		return type.isWebSocket();
	}
	
	/**
	 * @return The port indicated for use by this endpoint.
	 */
	public int getPort() {
		return port;
	}

}
