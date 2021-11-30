package net.aionstudios.proteus.header;

import java.util.LinkedList;
import java.util.List;

public class ProteusHeader {
	
	private String key;
	private List<HeaderValue> values;
	
	protected ProteusHeader(String key, List<String> values) {
		this.key = key;
		boolean noSplit = false;
		boolean noKV = false;
		if (key.equals("Expires")) {
			noSplit = true;
		}
		if (key.equals("Sec-WebSocket-Key")) {
			noKV = true;
		}
		this.values = new LinkedList<>();
		for (String s : values) {
			this.values.add(new HeaderValue(s, noSplit, noKV));
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
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof String) return key.equals(o);
		if (!(o instanceof ProteusHeader)) return false;
		ProteusHeader other = (ProteusHeader) o;
		return key.equals(other.key);
	}
	
	@Override
	public int hashCode() {
		return key.hashCode();
	}
	
}
