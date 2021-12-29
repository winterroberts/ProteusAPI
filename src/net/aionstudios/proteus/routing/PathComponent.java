package net.aionstudios.proteus.routing;

public class PathComponent {
	
	private static PathComponent ANY = new PathComponent("*");
	
	private String component;
	
	public PathComponent(String component) {
		this.component = component;
	}
	
	public boolean isPathParam() {
		return component.startsWith(":");
	}
	
	public boolean matches(String comp) {
		if (isPathParam() || this.equals(ANY)) return true;
		return this.component.equals(comp);
	}
	
	@Override
	public String toString() {
		return component;
	}
	
	@Override
	public int hashCode() {
		return component.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof PathComponent) {
			return this.component.equals(((PathComponent) other).component);
		}
		return false;
	}

}
