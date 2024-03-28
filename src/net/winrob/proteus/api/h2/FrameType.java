package net.winrob.proteus.api.h2;

public enum FrameType {
	
	DATA((byte) 0x0, false),
	HEADERS((byte) 0x1, false),
	PRIORITY((byte) 0x2, true),
	RST_STREAM((byte) 0x3, true),
	SETTINGS((byte) 0x4, true),
	PUSH_PROMISE((byte) 0x5, false),
	PING((byte) 0x6, true),
	GOAWAY((byte) 0x7, true),
	WINDOW_UPDATE((byte) 0x8, true),
	CONTINUATION((byte) 0x9, false);

	private byte value;
	private boolean isCtrlFrame;
	
	private FrameType(byte typeCode, boolean isCtrlFrame) {
		this.value = typeCode;
		this.isCtrlFrame = isCtrlFrame;
	}
	
	public boolean isCtrlFrame() {
		return isCtrlFrame;
	}
	
	public byte getValue() {
		return value;
	}
	
	public static FrameType forValue(byte opCode) {
		switch(opCode) {
		case (0x0):
			return DATA;
		default:
		return null;
		}
	}
	
}
