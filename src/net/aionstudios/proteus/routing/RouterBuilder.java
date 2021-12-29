package net.aionstudios.proteus.routing;

import java.util.HashSet;
import java.util.Set;

import net.aionstudios.proteus.configuration.EndpointConfiguration;

public class RouterBuilder {

	private Set<Hostname> hosts;
	private EndpointConfiguration endpoint;
	
	public RouterBuilder(EndpointConfiguration ec) {
		hosts = new HashSet<>();
		endpoint = ec;
	}
	
	public void addHostname(Hostname h) {
		if (!hosts.contains(h)) {
			hosts.add(h);
		}
	}
	
	public Router build() {
		Set<Hostname> hosts = new HashSet<>();
		for (Hostname h : this.hosts) {
			hosts.add(h);
		}
		return new Router(hosts, endpoint);
	}
	
}
