package net.winrob.proteus.api.h2;

public enum H2Setting {

	SETTINGS_HEADER_TABLE_SIZE((short) 0x01),
	SETTINGS_ENABLE_PUSH((short) 0x02),
	SETTINGS_MAX_CONCURRENT_STREAMS((short) 0x03),
	SETTINGS_INITIAL_WINDOW_SIZE((short) 0x04),
	SETTINGS_MAX_FRAME_SIZE((short) 0x05),
	SETTINGS_MAX_HEADER_LIST_SIZE((short) 0x06);
	
	private final short identifier;
	
	private H2Setting(short identifier) {
		this.identifier = identifier;
	}
	
	public int getValue() {
		return identifier;
	}
	
	public static H2Setting forValue(short identifier) {
		switch (identifier) {
		case 0x01:
			return SETTINGS_HEADER_TABLE_SIZE;
		case 0x02:
			return SETTINGS_ENABLE_PUSH;
		case 0x03:
			return SETTINGS_MAX_CONCURRENT_STREAMS;
		case 0x04:
			return SETTINGS_INITIAL_WINDOW_SIZE;
		case 0x05:
			return SETTINGS_MAX_FRAME_SIZE;
		case 0x06:
			return SETTINGS_MAX_HEADER_LIST_SIZE;
		default:
			return null;
		}
	}
	
}
