package net.aionstudios.proteus.header;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.aionstudios.proteus.api.request.ParameterMap;

/**
 * A collection of values for an HTTP header.
 * 
 * @author Winter Roberts
 *
 */
public class HeaderValue {
	
	List<QualityValue> values;
	ParameterMap<String> params;

	protected HeaderValue(String headerValue, boolean noSplit, boolean noParts) {
		Map<String, String> ps = new HashMap<>();
		values = new LinkedList<>();
		String[] valueParts = {headerValue};
		if (!noSplit) {
			valueParts = headerValue.split(",");
		}
		for (String vp : valueParts) {
			if (!noParts) {
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
						values.add(qi >= 0 && qi <= 1 ? new QualityValue(part, qi) : new QualityValue(part));
					}
				}
			} else {
				values.add(new QualityValue(vp));
			}
		}
		params = new ParameterMap<>(ps);
	}
	
	/**
	 * @return The first {@link QualityValue} (parameter and highest quality), or null if there isn't one.
	 */
	public QualityValue getQualityValue() {
		if (values.size() > 0) {
			return values.get(0);
		}
		return null;
	}
	
	/**
	 * @return The first (default) vaLue for this header, or null if none exist.
	 */
	public String getValue() {
		return values.size() > 0 ? getQualityValue().getValue() : null;
	}
	
	/**
	 * @return A parameter map from all assigned values within this header definition.
	 */
	public ParameterMap<String> getParams() {
		return params;
	}
	
	/**
	 * @return A list of quality values (which may or may not actually have quality indicators) for each assigned value of this header.
	 */
	public List<QualityValue> getValues() {
		return Collections.unmodifiableList(values);
	}
	
}
