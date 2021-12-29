package net.aionstudios.proteus.routing;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.aionstudios.proteus.request.ParameterMap;

public class PathInterpreter {
	
	private List<PathComponent> components;
	
	private Map<Integer, String> pathParams;
	
	public PathInterpreter(String path) {
		components = new LinkedList<>();
		String[] comps = splitPath(path);
		pathParams = new HashMap<>();
		int i = 0;
		for (String c : comps) {
			PathComponent p = new PathComponent(c);
			components.add(p);
			if (p.isPathParam()) {
				pathParams.put(i, c.substring(1));
			}
			i++;
		}
	}
	
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
	
	public PathComprehension comprehend(String path) {
		Map<String, String> params = new HashMap<>();
		String[] parts = splitPath(path);
		if (matches(path)) {
			for (int i = 0; i < components.size(); i++) {
				if (components.get(i).isPathParam()) {
					params.put(pathParams.get(i), parts[i]);
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
