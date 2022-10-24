package net.aionstudios.proteus.api.websocket;

/**
 * Enumerates valid web socket closing codes
 * 
 * @author Winter Roberts
 *
 */
public enum ClosingCode {

	NORMAL((short) 1000),
	GOING_AWAY((short) 1001),
	PROTOCOL_ERROR((short) 1002),
	UNACCEPTABLE((short) 1003),
	NO_CODE((short) 1005),
	INCONSISTENT((short) 1007),
	VIOLATION((short) 1008),
	TOO_BIG((short) 1009),
	UNEXPECTED((short) 1010);
	
	private short code;
	
	private ClosingCode(short code) {
		this.code = code;
	}
	
	public short getCode() {
		return code;
	}
	
}
