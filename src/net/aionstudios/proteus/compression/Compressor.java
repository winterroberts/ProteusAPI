package net.aionstudios.proteus.compression;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.DeflaterInputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.nixxcode.jvmbrotli.dec.BrotliInputStream;
import com.nixxcode.jvmbrotli.enc.BrotliOutputStream;

public class Compressor {
	
	public static byte[] compress(String str, CompressionEncoding ce) throws IOException {
		return compress(str.getBytes(StandardCharsets.UTF_8), ce);
	}
	
	public static byte[] compress(byte[] bytes, CompressionEncoding ce) throws IOException {
		if ((bytes == null) || (bytes.length == 0)) {
			return null;
		}
		ByteArrayOutputStream obj = new ByteArrayOutputStream();
		OutputStream o;
		switch(ce) {
			case BR:
				o = new BrotliOutputStream(obj);
				break;
			case DEFLATE:
				o = new DeflaterOutputStream(obj);
				break;
			case GZIP:
				o = new GZIPOutputStream(obj);
				break;
			case NONE:
			default:
				return bytes;
		}
		o.write(bytes);
		o.flush();
		o.close();
		return obj.toByteArray();
	}
	
	public static byte[] decompress(byte[] bytes, CompressionEncoding ce) throws IOException {
		if ((bytes == null) || (bytes.length == 0)) {
			return null;
		}
		ByteArrayOutputStream obj = new ByteArrayOutputStream();
		InputStream in;
		switch(ce) {
			case BR:
				in = new BrotliInputStream(new ByteArrayInputStream(bytes));
				break;
			case DEFLATE:
				in = new DeflaterInputStream(new ByteArrayInputStream(bytes));
				break;
			case GZIP:
				in = new GZIPInputStream(new ByteArrayInputStream(bytes));
				break;
			case NONE:
			default:
				return bytes;
		}
		obj.write(in.readAllBytes());
		obj.flush();
		obj.close();
		return obj.toByteArray();
	}

}
