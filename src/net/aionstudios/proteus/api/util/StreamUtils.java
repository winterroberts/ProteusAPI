package net.aionstudios.proteus.api.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class StreamUtils {

	public static String readLine(InputStream inputStream, boolean removeReturn) throws IOException {
		byte[] bytes = readRawLine(inputStream, removeReturn);
	    return bytes != null ? new String(bytes, StandardCharsets.UTF_8) : "";
	}
	
	public static byte[] readRawLine(InputStream inputStream, boolean removeReturn) throws IOException {
		ByteArrayOutputStream b = readLineToStream(inputStream, removeReturn);
	    return b != null ? b.toByteArray() : null;
	}
	
	public static void consumeLine(InputStream inputStream) throws IOException {
	    boolean store = false;
	    int c;
	    for (c = inputStream.read(); c != -1; c = inputStream.read()) {
	    	if (!store && c == '\r') {
	    		store = true;
	    	} else {
	    		if (store) {
	    			if (c == '\n') {
	    				return;
	    			} else {
	    				store = false;
	    			}
	    		}
	    	}
	    }
	}
	
	private static ByteArrayOutputStream readLineToStream(InputStream inputStream, boolean removeReturn) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    boolean store = false;
	    int count = 0;
	    int c;
	    for (c = inputStream.read(); c != -1; c = inputStream.read()) {
	    	boolean endAfter = false;
	    	count ++;
	    	if (!store && c == '\r') {
	    		store = true;
	    	} else {
	    		if (store) {
	    			if (c == '\n') {
	    				if (!removeReturn) {
	    					endAfter = true;
	    					byteArrayOutputStream.write('\r');
	    				} else {
	    					break;
	    				}
	    			} else {
	    				store = false;
	    				byteArrayOutputStream.write('\r');
	    			}
	    		}
	    		byteArrayOutputStream.write(c);
	    	}
	    	if (endAfter) {
	    		break;
	    	}
	    }
	    byteArrayOutputStream.flush();
	    byteArrayOutputStream.close();
	    if (c == -1 && byteArrayOutputStream.size() == 0) {
	        return null;
	    }
	    return byteArrayOutputStream;
	}
	
	public static byte[] joinByteArray(byte[] byte1, byte[] byte2) {
		return ByteBuffer.allocate(byte1.length + byte2.length)
	            .put(byte1)
	            .put(byte2)
	            .array();
	}
	
	public static byte[] joinByteArrayWithNewLine(byte[] byte1, byte[] byte2) {
		return ByteBuffer.allocate(byte1.length + byte2.length + 2)
	            .put(byte1)
	            .put(byte2)
	            .put((byte) '\r')
	            .put((byte) '\n')
	            .array();
	}
	
}
