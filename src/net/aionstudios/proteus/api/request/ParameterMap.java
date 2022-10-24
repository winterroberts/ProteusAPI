package net.aionstudios.proteus.api.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A string-indexed map type wrapper.
 * 
 * @author Winter Roberts
 *
 * @param <T> The value type of this String-indexed map, which may be a collection type.
 */
public class ParameterMap<T> {
	
	private Map<String, T> parameters;
	
	/**
	 * Converts a Map of the String-key, value type to a new ParameterMap.
	 * The client should ensure the input map CANNOT be modified after insertion.
	 * 
	 * @param parameters Existing parameter map to seed this one.
	 */
	public ParameterMap(Map<String, T> parameters) {
		this.parameters = parameters;
	}
	
	protected ParameterMap() {
		this.parameters = new HashMap<>();
	}
	
	/**
	 * @return A set view of the keys contained in this map.
	 */
	public Set<String> keySet() {
		return Collections.unmodifiableSet(parameters.keySet());
	}
	
	/**
	 * @return A set view of the mappings contained in this map.
	 */
	public Set<Entry<String, T>> entrySet() {
		return Collections.unmodifiableSet(parameters.entrySet());
	}
	
	/**
	 * @param param The parameter key whose presence in this map is to be tested.
	 * @return True if the parameter key is contained in the map, false otherwise.
	 */
	public boolean hasParameter(String param) {
		return parameters.containsKey(param);
	}
	
	protected void putParameter(String key, T value) {
		parameters.put(key, value);
	}
	
	/**
	 * @param param The parameter key whose value is to be returned.
	 * @return The value associated with this key, or null if there is no mapping.
	 */
	public T getParameter(String param) {
		return parameters.get(param);
	}

}
