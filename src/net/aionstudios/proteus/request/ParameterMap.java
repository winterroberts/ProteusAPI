package net.aionstudios.proteus.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ParameterMap<T> {
	
	private Map<String, T> parameters;
	
	public ParameterMap(Map<String, T> parameters) {
		this.parameters = parameters;
	}
	
	protected ParameterMap() {
		this.parameters = new HashMap<>();
	}
	
	public Set<String> keySet() {
		return parameters.keySet();
	}
	
	public Set<Entry<String, T>> entrySet() {
		return parameters.entrySet();
	}
	
	public boolean hasParameter(String param) {
		return parameters.containsKey(param);
	}
	
	protected void putParameter(String key, T value) {
		parameters.put(key, value);
	}
	
	public T getParameter(String param) {
		return parameters.get(param);
	}

}
