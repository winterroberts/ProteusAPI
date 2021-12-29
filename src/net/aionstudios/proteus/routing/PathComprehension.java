package net.aionstudios.proteus.routing;

import java.util.HashMap;
import java.util.Map;

import net.aionstudios.proteus.request.ParameterMap;

public class PathComprehension {
	
	private PathInterpreter interpreter;
	private ParameterMap<String> pathParams;
	
	private String path;

	protected PathComprehension(PathInterpreter interpreter, String path) {
		this(interpreter, path, new ParameterMap<>(new HashMap<>()));
	}
	
	protected PathComprehension(PathInterpreter interpreter, String path, ParameterMap<String> pathParams) {
		this.interpreter = interpreter;
		this.path = path;
		this.pathParams = pathParams;
	}
	
	public ParameterMap<String> getPathParameters() {
		return pathParams;
	}
	
	public PathInterpreter getInterpreter() {
		return interpreter;
	}
	
	public String getPath() {
		return path;
	}
	
}
