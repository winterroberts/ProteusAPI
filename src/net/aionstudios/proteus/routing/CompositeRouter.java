package net.aionstudios.proteus.routing;

import java.util.HashMap;
import java.util.Map;

import net.aionstudios.proteus.configuration.EndpointType;

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
		return null;
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
		return null;
	}
	
	/**
	 * Finds a route for any hostname given the path from an HTTP request.
	 * 
	 * @param path The path of the request.
	 * @return The first matching {@link HttpRoute} or null if none exist.
	 */
	public HttpRoute getHttpWilcard(String path) {
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

}
