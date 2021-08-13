package net.aionstudios.proteus.header;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ProteusHeaderBuilder {
	
	private Map<String, List<String>> headers;
	
	public ProteusHeaderBuilder() {
		headers = new HashMap<>();
	}
	
	public void putHeader(String key, String value) {
		if (!headers.containsKey(key)) {
			headers.put(key, new LinkedList<>());
		}
		headers.get(key).add(value);
	}
	
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
	
	public ProteusHttpHeaders toHeaders() {
		return new ProteusHttpHeaders(this);
	}
	
	protected Map<String, List<String>> getHeaders() {
		return headers;
	}

}
