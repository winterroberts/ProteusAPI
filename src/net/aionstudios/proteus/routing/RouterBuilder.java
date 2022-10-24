package net.aionstudios.proteus.routing;

import java.util.HashSet;
import java.util.Set;

import net.aionstudios.proteus.configuration.EndpointConfiguration;

/**
 * Used to construct endpoint {@link Router}s.
 * 
 * @author Winter Roberts
 *
 */
public class RouterBuilder {

	private Set<Hostname> hosts;
	
	/**
	 * Creates a new router builder based on the provided configuration.
	 * 
	 * @param ec The {@link EndpointConfiguration} to construct this router builder from.
	 */
	public RouterBuilder() {
		hosts = new HashSet<>();
	}
	
	/**
	 * Adds the given {@link Hostname} to this router builder.
	 * 
	 * @param h The {@link Hostname} to add.
	 */
	public void addHostname(Hostname h) {
		if (!hosts.contains(h)) {
			hosts.add(h);
		}
	}
	
	/**
	 * @return The {@link Router}s that have been built.
	 */
	public Router build(EndpointConfiguration ec) {
		Set<Hostname> hosts = new HashSet<>();
		for (Hostname h : this.hosts) {
			hosts.add(h);
		}
		return new Router(hosts, ec);
	}
	
}
