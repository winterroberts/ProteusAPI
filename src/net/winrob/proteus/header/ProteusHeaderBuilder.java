package net.winrob.proteus.header;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Used to construct header information into unmodifiable {@link ProteusHeader}s for reception or transmission.
 * 
 * @author Winter Roberts
 *
 */
public class ProteusHeaderBuilder {
	
	private Map<String, List<String>> headers;
	
	private ProteusHeaderBuilder() {
		headers = new HashMap<>();
	}
	
	/**
	 * @return A new builder.
	 */
	public static ProteusHeaderBuilder newBuilder() {
		return new ProteusHeaderBuilder();
	}
	
	/**
	 * Puts the given value under the given key, creating the key entry if it does not exist.
	 * 
	 * @param key The key for this header, which may be a list of values.
	 * @param value The value to be inserted, which may not modify the value list if it is non-unique.
	 */
	public void putHeader(String key, String value) {
		if (!headers.containsKey(key)) {
			headers.put(key, new LinkedList<>());
		}
		headers.get(key).add(value);
	}
	
	/**
	 * @return A collections of strings of the HTTP header format, containing the values in this builder.
	 */
	public List<String> toList() {
		List<String> list = new LinkedList<>();
		for (Entry<String, List<String>> entry : headers.entrySet()) {
			String header = entry.getKey() + (entry.getValue().size() > 0 ? ": " : "");
			List<String> ls = entry.getValue();
			for (int i = 0; i < ls.size(); i++) {
				header += ls.get(i) + (i + 1 != ls.size() ? "; " : "");
			}
			list.add(header);
		}
		return list;
	}
	
	/**
	 * @return The built {@link ProteusHttpHeaders} representation of the current state of this builder.
	 */
	public ProteusHttpHeaders toHeaders() {
		return new ProteusHttpHeaders(this);
	}
	
	// Protected information for generating ProteusHttpHeaders from a builder.
	protected Map<String, List<String>> getHeaders() {
		return headers;
	}

}
