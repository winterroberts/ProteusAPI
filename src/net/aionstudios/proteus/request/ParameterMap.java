package net.aionstudios.proteus.request;

import java.util.Map;

public class ParameterMap {
	
	private Map<String, String> parameters;
	
	public ParameterMap(Map<String, String> parameters) {
		this.parameters = parameters;
	}
	
	public boolean hasParameter(String param) {
		return parameters.containsKey(param);
	}
	
	protected void putParameter(String key, String value) {
		parameters.put(key, value);
	}
	
	public String getParameter(String param) {
		return parameters.get(param);
	}

}
