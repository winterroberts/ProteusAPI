package net.aionstudios.proteus.header;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.aionstudios.proteus.request.ParameterMap;

public class HeaderValue {
	
	List<String> values;
	ParameterMap<String> params;

	public HeaderValue(String headerValue) {
		Map<String, String> ps = new HashMap<>();
		values = new LinkedList<>();
		String[] parts = headerValue.split(";");
		for (String part : parts) {
			part = part.trim();
			String[] kv = part.split("=", 2);
			if (kv.length == 2) {
				ps.put(kv[0], kv[1]);
			} else {
				values.add(part);
			}
		}
		params = new ParameterMap<>(ps);
	}
	
	public String getValue() {
		if (values.size() > 0) {
			return values.get(0);
		}
		return null;
	}
	
	public ParameterMap<String> getParams() {
		return params;
	}
	
	public List<String> getValues() {
		return values;
	}
	
}
