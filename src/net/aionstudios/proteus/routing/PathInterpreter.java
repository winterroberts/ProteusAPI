package net.aionstudios.proteus.routing;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.aionstudios.proteus.request.ParameterMap;

/**
 * Combines {@link PathComponents} to interpret request path matches.
 * 
 * @author Winter Roberts
 *
 */
public class PathInterpreter {
	
	private List<PathComponent> components;
	
	private Map<Integer, String> pathParams;
	
	/**
	 * Creates a new path interpreter.
	 * 
	 * @param path The {@link PathComponent} form description for this path.
	 */
	public PathInterpreter(String path) {
		components = new LinkedList<>();
		String[] comps = splitPath(path);
		pathParams = new HashMap<>();
		int i = 0;
		for (String c : comps) {
			PathComponent p = new PathComponent(c);
			components.add(p);
			if (p.isPathParam()) {
				pathParams.put(i, URLDecoder.decode(c.substring(1), StandardCharsets.UTF_8));
			}
			i++;
		}
	}
	
	/**
	 * @param path The request path to be matched.
	 * @return True if the path matches, false otherwise.
	 */
	public boolean matches(String path) {
		String[] parts = splitPath(path);
		if (components.size() == parts.length) {
			for (int i = 0; i < components.size(); i++) {
				if (!components.get(i).matches(parts[i])) return false;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Converts the given request path to a {@link PathComprehension} based on this interpreter.
	 * 
	 * @param path The request path to be interpreted.
	 * @return The {@link PathComprehension} of the request path via this interpreter, or null if the interpreter could not comprehend the path.
	 */
	public PathComprehension comprehend(String path) {
		Map<String, String> params = new HashMap<>();
		String[] parts = splitPath(path);
		if (matches(path)) {
			for (int i = 0; i < components.size(); i++) {
				if (components.get(i).isPathParam()) {
					params.put(pathParams.get(i), URLDecoder.decode(parts[i], StandardCharsets.UTF_8));
				}
			}
			return new PathComprehension(this, path, new ParameterMap<>(params));
		}
		return null;
	}

	private static String[] splitPath(String path) { 
		if (!path.startsWith("/")) {
			path = path.substring(1);
		}
		return path.split("\\/");
	}
	
	@Override
	public int hashCode() {
		int hashSum = 0;
		for (PathComponent c : components) {
			hashSum += c.hashCode();
		}
		return hashSum;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof PathInterpreter) {
			if (((PathInterpreter) other).components.size() == components.size()) {
				for (int i = 0; i < components.size(); i++) {
					if (!components.get(i).equals(((PathInterpreter) other).components.get(i))) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
	
}
