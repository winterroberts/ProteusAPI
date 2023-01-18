package net.aionstudios.proteus.routing;

import java.util.Collections;
import java.util.Set;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;

import net.aionstudios.proteus.api.context.ProteusHttpContext;
import net.aionstudios.proteus.api.context.ProteusWebSocketContext;
import net.aionstudios.proteus.configuration.EndpointConfiguration;

/**
 * Routes requests for a single {@link EndpointConfiguration} associated with one or more unique {@link Hostname}s.
 * @author wrpar
 *
 */
public class Router {
	
	private Set<Hostname> hosts;
	private EndpointConfiguration endpoint;

	protected Router(Set<Hostname> hosts, EndpointConfiguration endpoint) {
		this.hosts = hosts;
		this.endpoint = endpoint;
	}
	
	/**
	 * Finds an {@link HttpRoute} for the given {@link Hostname} and request path.
	 * 
	 * @param host The {@link Hostname} targeted by the request.
	 * @param path The request path.
	 * @return The {@link HttpRoute} named by this request or null if no route exist.
	 */
	public HttpRoute getHttpRoute(Hostname host, String path) {
		if (this.hosts.contains(host) || this.hosts.contains(Hostname.ANY)) {
			return endpoint.getContextController().getHttpRoute(path);
		}
		return null;
	}
	
	/**
	 * Finds an {@link WebSocketRoute} for the given {@link Hostname} and request path.
	 * 
	 * @param host The {@link Hostname} targeted by the request.
	 * @param path The request path.
	 * @return The {@link HttpRoute} named by this request or null if no route exist.
	 */
	public WebSocketRoute getWebSocketRoute(Hostname host, String path) {
		if (this.hosts.contains(host) || this.hosts.contains(Hostname.ANY)) {
			return endpoint.getContextController().getWebSocketRoute(path);
		}
		return null;
	}
	
	/**
	 * @return A new {@link CompositeRouter} which contains this router.
	 */
	public CompositeRouter toComposite() {
		return new CompositeRouter(this);
	}
	
	/**
	 * @return A new {@link CompositeRouter} which contains this router.
	 */
	public CompositeRouter toComposite(SSLServerSocketFactory sslFactory) {
		return new CompositeRouter(sslFactory, this);
	}
	
	/**
	 * @return The {@link EndpointConfiguration} used by this router.
	 */
	protected EndpointConfiguration getEndpoint() {
		return endpoint;
	}
	
	/**
	 * @return The set of {@link Hostname}s accepted by this router.
	 */
	protected Set<Hostname> getHosts() {
		return Collections.unmodifiableSet(hosts);
	}
	
	/**
	 * @return The port for which this router accepts traffic.
	 */
	public int getPort() {
		return endpoint.getPort();
	}
	
	/**
	 * @return The default {@link ProteusHttpContext} from the {@link EndpointConfiguration} this router uses, which may be null.
	 */
	public ProteusHttpContext getDefaultHttpContext() {
		return endpoint.getContextController().getHttpDefault();
	}
	
	/**
	 * @return The default {@link ProteusWebSocketContext} from the {@link EndpointConfiguration} this router uses, which may be null.
	 */
	public ProteusWebSocketContext getDefaultWebSocketContext() {
		return endpoint.getContextController().getWebSocketDefault();
	}
	
}
