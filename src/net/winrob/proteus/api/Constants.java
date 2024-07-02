package net.winrob.proteus.api;

/**
 * This class holds application constants that may be used by multiple files.
 * 
 * @author Winter Roberts
 *
 */
public class Constants {
	
	// 2^20 bytes per mebibyte, 20 MiB. Used for HTTP/1.1 file upload and WebSocket requests. 
	public static final int MAX_PAYLOAD = 20971520;
	
	public static final String CONNECTION_PREFACE = "PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n";
	
	public static final int SETTINGS_MAX_FRAME_SIZE = 16384;

}
