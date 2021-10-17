package net.aionstudios.proteus.header;

public class QualityValue {
	
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
	
	public String getValue() { 
		return value;
	}
	
	public boolean hasQuality() {
		return hasQuality;
	}
	
	public double getQuality() {
		return hasQuality ? quality : -1;
	}

}
