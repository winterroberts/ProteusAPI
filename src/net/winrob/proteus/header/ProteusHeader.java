package net.winrob.proteus.header;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * An HTTP header, which contains a collections of one or more {@link HeaderValue}s.
 * 
 * @author Winter Roberts
 *
 */
public class ProteusHeader {
	
	private String key;
	private List<HeaderValue> values;
	
	// Assigns special flags and decomposes collectec values into HeaderValues.
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
	
	/**
	 * @return The key name for which this header contains {@link HeaderValue}s.
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * @return The number of {@link HeaderValues} assigned to this header.
	 */
	public int size() {
		return values.size();
	}
	
	/**
	 * @param idx An index into the headers list.
	 * @return The {@link HeaderValue} at the given index if it exists.
	 * @throws IndexOutOfBoundsException if the index is outside the boundary of the headers list.
	 */
	public HeaderValue get(int idx) {
		return values.get(idx);
	}
	
	/**
	 * @return A list of all header values.
	 */
	public List<HeaderValue> getValues() {
		return Collections.unmodifiableList(values);
	}
	
	/**
	 * @return The first header value (which may be considered the only valid setting) in the header list.
	 */
	public HeaderValue getFirst() {
		if (values.size() > 0) {
			return values.get(0);
		}
		return null;
	}
	
	/**
	 * @return The last header value (which may also be the first, but is often not considered the default otherwise) in the header list.
	 */
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
