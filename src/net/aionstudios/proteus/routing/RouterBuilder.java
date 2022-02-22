package net.aionstudios.proteus.routing;

import java.util.HashSet;
import java.util.Set;

import net.aionstudios.proteus.configuration.EndpointConfiguration;
import net.aionstudios.proteus.header.ProteusHeader;

/**
 * Used to construct endpoint {@link Router}s.
 * 
 * @author Winter Roberts
 *
 */
public class RouterBuilder {

	private Set<Hostname> hosts;
	private EndpointConfiguration endpoint;
	
	/**
	 * Creates a new router builder based on the provided configuration.
	 * 
	 * @param ec The {@link EndpointConfiguration} to construct this router builder from.
	 */
	public RouterBuilder(EndpointConfiguration ec) {
		hosts = new HashSet<>();
		endpoint = ec;
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
	 * @return The {@link Router} that has been built.
	 */
	public Router build() {
		Set<Hostname> hosts = new HashSet<>();
		for (Hostname h : this.hosts) {
			hosts.add(h);
		}
		return new Router(hosts, endpoint);
	}
	
}
