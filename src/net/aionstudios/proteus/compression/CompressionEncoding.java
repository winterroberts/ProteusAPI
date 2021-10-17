package net.aionstudios.proteus.compression;

import net.aionstudios.proteus.api.ProteusAPI;

public enum CompressionEncoding {
	
	NONE(null),
	BR("br"),
	DEFLATE("deflate"),
	GZIP("gzip");
	
	private String name;
	
	private CompressionEncoding(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
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
