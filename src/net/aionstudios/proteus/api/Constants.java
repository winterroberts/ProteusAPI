package net.aionstudios.proteus.api;

/**
 * This class holds application constants that may be used by multiple files.
 * 
 * @author Winter Roberts
 *
 */
public class Constants {
	
	// 2^20 bytes per mebibyte, 20 MiB. Used for HTTP/1.1 file upload and WebSocket requests. 
	public static int MAX_PAYLOAD = 20971520;

}
