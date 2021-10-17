package net.aionstudios.proteus.header;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.aionstudios.proteus.request.ParameterMap;

public class HeaderValue {
	
	List<QualityValue> values;
	ParameterMap<String> params;

	protected HeaderValue(String headerValue, boolean noSplit) {
		Map<String, String> ps = new HashMap<>();
		values = new LinkedList<>();
		String[] valueParts = {headerValue};
		if (!noSplit) {
			valueParts = headerValue.split(",");
		}
		for (String vp : valueParts) {
			String[] parts = vp.split("((;)(?!(\\s*q=[0-9])))");
			for (String part : parts) {
				double qi = -1;
				part = part.trim();
				if (part.contains(";")) {
					String[] quality = part.split(";", 2);
					part = quality[0];
					String qv = quality[1];
					String[] qkv = qv.split("=", 2);
					qi = Double.parseDouble(qkv[1]);
				}
				String[] kv = part.split("=", 2);
				if (kv.length == 2) {
					ps.put(kv[0], kv[1]);
				} else {
					values.add(qi >= 0 & qi <= 1 ? new QualityValue(part, qi) : new QualityValue(part));
				}
			}
		}
		params = new ParameterMap<>(ps);
	}
	
	public QualityValue getQualityValue() {
		if (values.size() > 0) {
			return values.get(0);
		}
		return null;
	}
	
	public String getValue() {
		return values.size() > 0 ? getQualityValue().getValue() : null;
	}
	
	public ParameterMap<String> getParams() {
		return params;
	}
	
	public List<QualityValue> getValues() {
		return values;
	}
	
}
