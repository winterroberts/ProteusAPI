package net.winrob.proteus.routing;

import java.util.HashMap;

import net.winrob.proteus.api.request.ParameterMap;

/**
 * The interpretation of a request path by an interpreter (with path parameters saved)
 * 
 * @author Winter Roberts
 *
 */
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
	
	/**
	 * @return A {@link ParameterMap<String>} containing the path parameters of the request, which may be empty.
	 */
	public ParameterMap<String> getPathParameters() {
		return pathParams;
	}
	
	/**
	 * @return The {@link PathInterpreter} which accepted the request path.
	 */
	public PathInterpreter getInterpreter() {
		return interpreter;
	}
	
	/**
	 * @return The raw value of the path component of the request URL.
	 */
	public String getPath() {
		return path;
	}
	
}
