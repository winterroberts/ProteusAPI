package net.aionstudios.proteus.compression;

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
	
	public static CompressionEncoding forAcceptHeader(String acceptHeader, boolean brotliEnabled) {
		if(acceptHeader != null) {
			if(brotliEnabled&&acceptHeader.contains("br")&&!acceptHeader.contains("br;q=0")&&!acceptHeader.contains("br; q=0")){
				return CompressionEncoding.BR;
			} else if(acceptHeader.contains("gzip")&&!acceptHeader.contains("gzip;q=0")&&!acceptHeader.contains("gzip; q=0")){
				return CompressionEncoding.GZIP;
			} else if (acceptHeader.contains("deflate")&&!acceptHeader.contains("deflate;q=0")&&!acceptHeader.contains("deflate; q=0")) {
				return CompressionEncoding.DEFLATE;
			}
		}
		return CompressionEncoding.NONE;
	}

}
