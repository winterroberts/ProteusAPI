package net.winrob.proteus.routing;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;

import net.winrob.proteus.configuration.EndpointType;

/**
 * Combines multiple routers (which may come from multiple {@link ProteusImplementers} that target different hosts) into a single endpoint router
 * 
 * @author Winter Roberts
 *
 */
public class CompositeRouter {
	
	private Map<Hostname, Router> hostMap;
	private int port = -1;
	
	private EndpointType et;
	
	private SSLServerSocketFactory sslFactory;

	/**
	 * Creates an empty secure composite router.
	 */
	public CompositeRouter(SSLServerSocketFactory sslFactory) {
		hostMap = new HashMap<>();
		this.sslFactory = sslFactory;
	}
	
	/**
	 * Creates an empty composite router.
	 */
	public CompositeRouter() {
		hostMap = new HashMap<>();
	}
	
	/**
	 * Creates a composite router which consists of the given router.
	 * 
	 * @param router1 A router.
	 */
	public CompositeRouter(Router router1) {
		this();
		port = router1.getPort();
		et = router1.getEndpoint().getType();
		addRouter(router1);
	}
	
	/**
	 * Creates a secure composite router which consists of the given router.
	 * 
	 * @param router1 A router.
	 */
	public CompositeRouter(SSLServerSocketFactory sslFactory, Router router1) {
		this(sslFactory);
		port = router1.getPort();
		et = router1.getEndpoint().getType();
		addRouter(router1);
	}
	
	/**
	 * Creates a composite router which consists of the given routers.
	 * 
	 * @param router1 The first router.
	 * @param router2 One or more routers.
	 */
	public CompositeRouter(Router router1, Router... router2) {
		this(router1);
		for (Router r : router2) {
			addRouter(r);
		}
	}
	
	/**
	 * Creates a secure composite router which consists of the given routers.
	 * 
	 * @param router1 The first router.
	 * @param router2 One or more routers.
	 */
	public CompositeRouter(SSLServerSocketFactory sslFactory, Router router1, Router... router2) {
		this(sslFactory, router1);
		for (Router r : router2) {
			addRouter(r);
		}
	}
	
	/**
	 * Copies and adds to an existing composite router.
	 * 
	 * @param router1 The composite router to copy.
	 * @param router2 One or more routers to be added.
	 */
	public CompositeRouter(CompositeRouter router1, Router... router2) {
		this();
		hostMap.putAll(router1.getHostMap());
		port = router1.port;
		for (Router r : router2) {
			addRouter(r);
		}
	}
	
	private boolean addRouter(Router router) {
		for (Hostname host : router.getHosts()) {
			if (hostMap.containsKey(host)) {
				System.err.println("Duplicate hostname in composite router: " + host.getHostname());
				return false;
			} else if (port > 0 && router.getPort() != port) {
				System.err.println("Port mismatch in composite router: had " + port + " but got " + router.getPort());
				return false;
			}
			if (port < 0) {
				port = router.getPort();
			}
			if (router.getEndpoint().getType() != et) {
				et = EndpointType.MIXED;
			}
 			hostMap.put(host, router);
		}
		return true;
	}
	
	private Map<Hostname, Router> getHostMap() {
		return hostMap;
	}
	
	/**
	 * @return the {@link EndpointType} of this router, which may not describe the behavior of indivdual routes.
	 */
	public EndpointType getType() {
		return et;
	}
	
	/**
	 * @return The port this router endpoint accepts traffic over.
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * Finds a route given the hostname and path from an HTTP request.
	 * 
	 * @param host The {@link Hostname} of the request.
	 * @param path The path of the request.
	 * @return The first matching {@link HttpRoute} or null if none exist.
	 */
	public HttpRoute getHttpRoute(Hostname host, String path) {
		if (hostMap.containsKey(host)) {
			return hostMap.get(host).getHttpRoute(host, path);
		}
		return getHttpWildcard(path);
	}
	
	/**
	 * Finds a route given the hostname and path from an web socket request.
	 * 
	 * @param host The {@link Hostname} of the request.
	 * @param path The path of the request.
	 * @return The first matching {@link WebSocketRoute} or null if none exist.
	 */
	public WebSocketRoute getWebSocketRoute(Hostname host, String path) {
		if (hostMap.containsKey(host)) {
			return hostMap.get(host).getWebSocketRoute(host, path);
		}
		return getWebSocketWildcard(path);
	}
	
	/**
	 * Finds a route for any hostname given the path from an HTTP request.
	 * 
	 * @param path The path of the request.
	 * @return The first matching {@link HttpRoute} or null if none exist.
	 */
	public HttpRoute getHttpWildcard(String path) {
		if (hostMap.containsKey(Hostname.ANY)) {
			return hostMap.get(Hostname.ANY).getHttpRoute(Hostname.ANY, path);
		}
		return null;
	}
	
	/**
	 * Finds a route for any hostname given the path from an web socket request.
	 * 
	 * @param path The path of the request.
	 * @return The first matching {@link WebSocketRoute} or null if none exist.
	 */
	public WebSocketRoute getWebSocketWildcard(String path) {
		if (hostMap.containsKey(Hostname.ANY) ) {
			return hostMap.get(Hostname.ANY).getWebSocketRoute(Hostname.ANY, path);
		}
		return null;
	}
	
	public boolean isSecure() {
		return sslFactory != null;
	}
	
	public ServerSocket createSocket() throws IOException {
		if (sslFactory != null) {
			SSLServerSocket sslSocket = (SSLServerSocket) sslFactory.createServerSocket(port);
			sslSocket.setUseClientMode(false);
			sslSocket.setWantClientAuth(false);
			sslSocket.setNeedClientAuth(false);
			sslSocket.setEnabledCipherSuites(sslSocket.getSupportedCipherSuites());
			sslSocket.setEnabledProtocols(sslSocket.getSupportedProtocols());
			return sslSocket;
		}
		return new ServerSocket(port);
	}

}
