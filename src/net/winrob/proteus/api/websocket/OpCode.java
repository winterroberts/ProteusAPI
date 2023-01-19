package net.winrob.proteus.api.websocket;

/**
 * Enumerates valid web socket operation codes.
 * 
 * @author Winter Roberts
 *
 */
public enum OpCode {
	
	CONTINUATION((byte) 0x0, false),
	TEXT((byte) 0x1, false),
	BINARY((byte) 0x2, false),
	CLOSE((byte) 0x8, true),
	PING((byte) 0x9, true),
	PONG((byte) 0xA, true);
	
	private byte value;
	
	private boolean isCtrlFrame;
	
	private OpCode(byte opCode, boolean isCtrlFrame) {
		this.value = opCode;
		this.isCtrlFrame = isCtrlFrame;
	}
	
	public boolean isCtrlFrame() {
		return isCtrlFrame;
	}
	
	public byte getValue() {
		return value;
	}
	
	public static OpCode forValue(byte opCode) {
		switch(opCode) {
		case (0x0):
			return CONTINUATION;
		case (0x1):
			return TEXT;
		case (0x2):
			return BINARY;
		case (0x8):
			return CLOSE;
		case (0x9):
			return PING;
		case (0xA):
			return PONG;
		default:
		return null;
		}
	}

}
