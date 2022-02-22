package net.aionstudios.proteus.compression;

import net.aionstudios.proteus.api.ProteusAPI;

/**
 * An enumeration of the encodings to compress messages on the server.
 * 
 * @author Winter Roberts
 *
 */
public enum CompressionEncoding {
	
	NONE(null),
	BR("br"),
	DEFLATE("deflate"),
	GZIP("gzip");
	
	private String name;
	
	private CompressionEncoding(String name) {
		this.name = name;
	}
	
	/**
	 * @return The string name of this {@link CompressionEncoding}, which may be null.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Reads the string value of an HTTP/1.1 "Accept-Encoding" header in order to determine the best content encoding
	 * choice for the response.
	 * The "preferred" encoding is the highest selected in order from "br", "gzip", "deflate", and "none" which is
	 * supported by both the client and the server.
	 * 
	 * @param acceptHeader The string value of the accept header.
	 * @return The preferred {@link CompressionEncoding}.
	 */
	public static CompressionEncoding forAcceptHeader(String acceptHeader) {
		if(acceptHeader != null) {
			if(ProteusAPI.hasBrotli()&&acceptHeader.contains("br")&&!acceptHeader.contains("br;q=0")&&!acceptHeader.contains("br; q=0")){
				return CompressionEncoding.BR;
			} else if(acceptHeader.contains("gzip")&&!acceptHeader.contains("gzip;q=0")&&!acceptHeader.contains("gzip; q=0")){
				return CompressionEncoding.GZIP;
			} else if (acceptHeader.contains("deflate")&&!acceptHeader.contains("deflate;q=0")&&!acceptHeader.contains("deflate; q=0")) {
				return CompressionEncoding.DEFLATE;
			}
		}
		return CompressionEncoding.NONE;
	}
	
	/**
	 * Returns the compression encoding named by this string, or null if it doesn't exist.
	 * 
	 * @param name The string name of the compression encoding to be found.
	 * @return The {@link CompressionEncoding} or null if it does not exist.
	 */
	public static CompressionEncoding forName(String name) {
		if(name != null) {
			if(ProteusAPI.hasBrotli()&&name.contains("br")){
				return CompressionEncoding.BR;
			} else if(name.contains("gzip")){
				return CompressionEncoding.GZIP;
			} else if (name.contains("deflate")) {
				return CompressionEncoding.DEFLATE;
			}
		}
		return CompressionEncoding.NONE;
	}

}
