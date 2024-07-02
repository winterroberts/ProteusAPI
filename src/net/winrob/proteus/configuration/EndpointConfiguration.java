package net.winrob.proteus.configuration;

import java.util.Set;

import javax.net.ssl.SSLContext;

/**
 * Used to configure {@link ProteusServer} resources, allocating routes and HTTP/1.1 and/or WebSocket ports.
 * 
 * @author Winter Roberts
 *
 */
public class EndpointConfiguration {
	
	private Set<EndpointType> types;
	private int port;
	
	private ContextController controller;
	
	/**
	 * Creates a new unsecured endpoint configuration.
	 * 
	 * @param type The {@link EndpointType} of this endpoint, which indicates HTTP, WebSocket, or both.
	 * @param port The port this endpoint should open at.
	 */
	public EndpointConfiguration(int port, Set<EndpointType> types) {
		this(ContextController.newInstance(), port, types);
	}
	
	public EndpointConfiguration(ContextController ctxController, int port, Set<EndpointType> types) {
		this.port = port;
		this.types = types;
		controller = ctxController;
	}
	
	/**
	 * @return The {@link EndpointType} of this endpoint.
	 */
	public Set<EndpointType> getTypes() {
		return types;
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
		return this.isHttp11() || this.isHttp2();
	}
	
	public boolean isHttp11() {
		return types.contains(EndpointType.HTTP1_1);
	}
	
	public boolean isHttp2() {
		return types.contains(EndpointType.HTTP2);
	}
	
	/**
	 * @return True if the {@link EndpointType} used to instantiate this endpoint indicated WebSocket server is enabled, false otherwise.
	 */
	public boolean isWebSocket() {
		return types.contains(EndpointType.WEBSOCKET);
	}
	
	/**
	 * @return The port indicated for use by this endpoint.
	 */
	public int getPort() {
		return port;
	}

}
