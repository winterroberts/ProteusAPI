package net.aionstudios.proteus.routing;

import java.util.HashMap;
import java.util.Map;

import net.aionstudios.proteus.configuration.EndpointType;

public class CompositeRouter {
	
	private Map<Hostname, Router> hostMap;
	private int port = -1;
	
	private EndpointType et;

	public CompositeRouter() {
		hostMap = new HashMap<>();
	}
	
	public CompositeRouter(Router router1) {
		this();
		port = router1.getPort();
		et = router1.getEndpoint().getType();
		addRouter(router1);
	}
	
	public CompositeRouter(Router router1, Router router2) {
		this(router1);
		addRouter(router2);
	}
	
	public CompositeRouter(CompositeRouter router1, Router router2) {
		this();
		hostMap.putAll(router1.getHostMap());
		port = router1.port;
		addRouter(router2);
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
	
	public EndpointType getType() {
		return et;
	}
	
	public int getPort() {
		return port;
	}
	
	public HttpRoute getHttpRoute(Hostname host, String path) {
		if (hostMap.containsKey(host)) {
			return hostMap.get(host).getHttpRoute(host, path);
		}
		return null;
	}
	
	public WebSocketRoute getWebSocketRoute(Hostname host, String path) {
		if (hostMap.containsKey(host)) {
			return hostMap.get(host).getWebSocketRoute(host, path);
		}
		return null;
	}
	
	public HttpRoute getHttpWilcard(String path) {
		if (hostMap.containsKey(Hostname.ANY)) {
			return hostMap.get(Hostname.ANY).getHttpRoute(Hostname.ANY, path);
		}
		return null;
	}
	
	public WebSocketRoute getWebSocketWildcard(String path) {
		if (hostMap.containsKey(Hostname.ANY) ) {
			return hostMap.get(Hostname.ANY).getWebSocketRoute(Hostname.ANY, path);
		}
		return null;
	}

}
