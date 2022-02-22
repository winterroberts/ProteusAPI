package net.aionstudios.proteus.header;

/**
 * A header value which may have an associated quality value.
 * 
 * @author Winter Roberts
 *
 */
public class QualityValue implements Comparable<QualityValue> {
	
	private String value;
	private double quality;
	
	private boolean hasQuality;
	
	protected QualityValue(String value, double quality) {
		hasQuality = true;
		this.quality = quality;
		this.value = value;
	}
	
	protected QualityValue(String value) {
		this(value, -1);
		hasQuality = false;
	}
	
	/**
	 * @return The string value (ignorant to quality) of this QualityValue.
	 */
	public String getValue() { 
		return value;
	}
	
	/**
	 * @return True is this QualityValue actually has an associated quality value, false otherwise.
	 */
	public boolean hasQuality() {
		return hasQuality;
	}
	
	/**
	 * @return The quality value of this QualityValue, or -1 if there is not one.
	 */
	public double getQuality() {
		return hasQuality ? quality : -1;
	}

	@Override
	public int compareTo(QualityValue o) {
		if (this.hasQuality) {
			return this.getQuality() > o.getQuality() ? 1 : -1;
		} else if (o.hasQuality()) {
			return o.compareTo(this);
		} else {
			return 0;
		}
	}

}
