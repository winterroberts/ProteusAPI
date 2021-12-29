package net.aionstudios.proteus.routing;

public class Hostname {
	
	public static Hostname ANY = new Hostname("*");
	
	private String host;
	
	public Hostname(String hostname) {
		this.host = hostname;
	}
	
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
	
	public String getHostname() {
		return host;
	}

}
