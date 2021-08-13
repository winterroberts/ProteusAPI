package net.aionstudios.proteus.header;

import java.util.LinkedList;
import java.util.List;

public class ProteusHeader {
	
	private String key;
	private List<HeaderValue> values;
	
	protected ProteusHeader(String key, List<String> values) {
		this.key = key;
		this.values = new LinkedList<>();
		for (String s : values) {
			this.values.add(new HeaderValue(s));
		}
	}
	
	protected void addValue(HeaderValue value) {
		values.add(value);
	}
	
	public String getKey() {
		return key;
	}
	
	public int size() {
		return values.size();
	}
	
	public HeaderValue get(int idx) {
		return values.get(idx);
	}
	
	public List<HeaderValue> getValues() {
		return values;
	}
	
	public HeaderValue getFirst() {
		if (values.size() > 0) {
			return values.get(0);
		}
		return null;
	}
	
	public HeaderValue getLast() {
		if (values.size() > 0) {
			return values.get(values.size()-1);
		}
		return null;
	}
	
}
