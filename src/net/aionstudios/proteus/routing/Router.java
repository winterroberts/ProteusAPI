package net.aionstudios.proteus.routing;

import java.util.Set;

import net.aionstudios.proteus.api.context.ProteusHttpContext;
import net.aionstudios.proteus.api.context.ProteusWebSocketContext;
import net.aionstudios.proteus.configuration.EndpointConfiguration;

public class Router {
	
	private Set<Hostname> hosts;
	private EndpointConfiguration endpoint;

	protected Router(Set<Hostname> hosts, EndpointConfiguration endpoint) {
		this.hosts = hosts;
		this.endpoint = endpoint;
	}
	
	public HttpRoute getHttpRoute(Hostname host, String path) {
		if (this.hosts.contains(host) || this.hosts.contains(Hostname.ANY)) {
			return endpoint.getContextController().getHttpRoute(path);
		}
		return null;
	}
	
	public WebSocketRoute getWebSocketRoute(Hostname host, String path) {
		if (this.hosts.contains(host) || this.hosts.contains(Hostname.ANY)) {
			return endpoint.getContextController().getWebSocketRoute(path);
		}
		return null;
	}
	
	public CompositeRouter toComposite() {
		return new CompositeRouter(this);
	}
	
	protected EndpointConfiguration getEndpoint() {
		return endpoint;
	}
	
	protected Set<Hostname> getHosts() {
		return hosts;
	}
	
	public int getPort() {
		return endpoint.getPort();
	}
	
	public ProteusHttpContext getDefaultHttpContext() {
		return endpoint.getContextController().getHttpDefault();
	}
	
	public ProteusWebSocketContext getDefaultWebSocketContext() {
		return endpoint.getContextController().getWebSocketDefault();
	}
	
}
