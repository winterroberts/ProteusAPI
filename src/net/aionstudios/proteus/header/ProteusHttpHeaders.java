package net.aionstudios.proteus.header;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Collection of {@link ProteusHeader}s.
 * 
 * @author Winter Roberts
 *
 */
public class ProteusHttpHeaders {
	
	private Map<String, ProteusHeader> headers;
	
	protected ProteusHttpHeaders(ProteusHeaderBuilder builder) {
		headers = new HashMap<>();
		for (Entry<String, List<String>> header : builder.getHeaders().entrySet()) {
			List<String> into = new LinkedList<>();
			for (String s : header.getValue()) {
				into.add(s.trim());
			}
			headers.put(header.getKey(), new ProteusHeader(header.getKey(), into));
		}
	}
	
	/**
	 * @return The header key set representing the state of this collection.
	 */
	public Set<String> headers() {
		return Collections.unmodifiableSet(headers.keySet());
	}
	
	/**
	 * @param key The key to be searched for.
	 * @return True if this collection contains a header with the given key, false otherwise.
	 */
	public boolean hasHeader(String key) {
		return headers.containsKey(key);
	}
	
	/**
	 * @param key The key whose associated value is to be returned.
	 * @return The {@link ProteusHeader} of this key, or null if there is no associated header.
	 */
	public ProteusHeader getHeader(String key) {
		if (headers.containsKey(key)) {
			return headers.get(key);
		}
		return null;
	}

}
