package net.aionstudios.proteus.header;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ProteusHttpHeaders {
	
	private Map<String, List<String>> headers;
	
	protected ProteusHttpHeaders(ProteusHeaderBuilder builder) {
		headers = new HashMap<>();
		for (Entry<String, List<String>> header : builder.getHeaders().entrySet()) {
			List<String> into;
			if (!headers.containsKey(header.getKey())) {
				into = new LinkedList<>();
				headers.put(header.getKey(), into);
			} else {
				into = headers.get(header.getKey());
			}
			for (String s : header.getValue()) {
				into.add(s);
			}
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
			return new ProteusHeader(key, headers.get(key));
		}
		return null;
	}

}
