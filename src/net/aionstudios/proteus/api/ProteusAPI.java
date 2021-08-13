package net.aionstudios.proteus.api;

import com.nixxcode.jvmbrotli.common.BrotliLoader;

public class ProteusAPI {
	
	private static boolean brotli = false;

	public static void enableBrotli() {
		brotli = BrotliLoader.isBrotliAvailable();
	}
	
	public static boolean hasBrotli() {
		return brotli;
	}
	
}
