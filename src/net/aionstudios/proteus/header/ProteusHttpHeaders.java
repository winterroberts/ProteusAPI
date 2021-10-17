package net.aionstudios.proteus.header;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
	
	public Set<String> headers() {
		return headers.keySet();
	}
	
	public boolean hasHeader(String key) {
		return headers.containsKey(key);
	}
	
	public ProteusHeader getHeader(String key) {
		if (headers.containsKey(key)) {
			return headers.get(key);
		}
		return null;
	}

}
