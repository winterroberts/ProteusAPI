package net.aionstudios.proteus.routing;

/**
 * A path component, which may be a path parameter or wildcard.
 * 
 * @author Winter Roberts
 *
 */
public class PathComponent {
	
	private static PathComponent ANY = new PathComponent("*");
	
	private String component;
	
	/**
	 * Creates a new path component of the named exact value (or any value if parameter or wildcard)
	 * 
	 * @param component The component name.
	 */
	public PathComponent(String component) {
		this.component = component;
	}
	
	/**
	 * @return True if this path component is a path parameters, false otherwise.
	 */
	public boolean isPathParam() {
		return component.startsWith(":");
	}
	
	/**
	 * @param comp The string path segment to compare to.
	 * @return True if the strings match (including wildcard and path parameter), false otherwise.
	 */
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
