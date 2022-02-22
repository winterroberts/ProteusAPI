package net.aionstudios.proteus.api;

import com.nixxcode.jvmbrotli.common.BrotliLoader;

/**
 * The base event controller for the application.
 * 
 * @author Winter Roberts
 *
 */
public class ProteusAPI {
	
	// Whether or not brotli encoding is supported by the machine
	private static boolean brotli = false;

	// Enables brotli and flags that it is available... Automatically called by the server.
	public static void enableBrotli() {
		if (!brotli) {
			brotli = BrotliLoader.isBrotliAvailable();
		}
	}
	
	// TODO move internal API here!
	
	/**
	 * @return True if brotli encoding is enabled on the machine, false otherwise.
	 */
	public static boolean hasBrotli() {
		return brotli;
	}
	
}
