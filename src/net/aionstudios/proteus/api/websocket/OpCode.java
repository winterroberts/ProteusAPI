package net.aionstudios.proteus.api.websocket;

/**
 * Enumerates valid web socket operation codes.
 * 
 * @author Winter Roberts
 *
 */
public enum OpCode {
	
	CONTINUATION((byte) 0x0),
	TEXT((byte) 0x1),
	BINARY((byte) 0x2),
	CLOSE((byte) 0x8),
	PING((byte) 0x9),
	PONG((byte) 0xA);
	
	private byte value;
	
	private OpCode(byte opCode) {
		this.value = opCode;
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
