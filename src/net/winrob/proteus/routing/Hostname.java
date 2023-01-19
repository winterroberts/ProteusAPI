package net.winrob.proteus.routing;

/**
 * A hostname, which may be a wildcard, used to identify traffic-accepting routes.
 * 
 * @author Winter Roberts
 *
 */
public class Hostname {
	
	public static Hostname ANY = new Hostname("*");
	public static Hostname LOOPBACK = new Hostname("127.0.0.1");
	public static Hostname LOCALHOST = new Hostname("localhost");
	
	private String host;
	
	/**
	 * Creates a new hostname for traffic on the given domain.
	 * 
	 * @param hostname The domain or subdomain for routing.
	 */
	public Hostname(String hostname) {
		this.host = hostname;
	}
	
	/**
	 * @param other A {@link Hostame} to compare to.
	 * @return True if the hostnames are equivalent, false otherwise.
	 */
	public boolean matches(Hostname other) {
		return this.equals(ANY) || other.equals(ANY) ? true : this.equals(other);
	}
	
	@Override
	public int hashCode() {
		return host.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Hostname) {
			return host.equals(((Hostname) other).getHostname());
		}
		return false;
	}
	
	/**
	 * @return The domain or subdomain string for this hostname.
	 */
	public String getHostname() {
		return host;
	}

}
