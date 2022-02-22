package net.aionstudios.proteus.api.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * A utility class which helps read input (especially HTTP/1.1-formatted input) from {@link InputStream}s.
 * 
 * @author Winter Roberts
 *
 */
public class StreamUtils {

	/**
	 * Reads the next line from the {@link InputStream}, removing the return character is requested, as a string.
	 * 
	 * @param inputStream The {@link InputStream} to be read from.
	 * @param removeReturn Whether or not the stream return (\r\n) should be removed from the line.
	 * @return The UTF-8 string value of the line.
	 * @throws IOException If there is a read failure.
	 */
	public static String readLine(InputStream inputStream, boolean removeReturn) throws IOException {
		byte[] bytes = readRawLine(inputStream, removeReturn);
	    return bytes != null ? new String(bytes, StandardCharsets.UTF_8) : "";
	}
	
	/**
	 * Reads the next line from the {@link InputStream}, removing the return character is requested.
	 * 
	 * @param inputStream The {@link InputStream} to be read from.
	 * @param removeReturn Whether or not the stream return (\r\n) should be removed from the line.
	 * @return The raw byte array-value of the line.
	 * @throws IOException If there is a read failure.
	 */
	public static byte[] readRawLine(InputStream inputStream, boolean removeReturn) throws IOException {
		ByteArrayOutputStream b = readLineToStream(inputStream, removeReturn);
	    return b != null ? b.toByteArray() : null;
	}
	
	/**
	 * Reads the next line (until \r\n) from the {@link InputStream} without returning the value.
	 * 
	 * @param inputStream The {@link InputStream} to be read from.
	 * @throws IOException If there is a read failure.
	 */
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
	
	/**
	 * Reads the next line from the {@link InputStream} and returns it as a {@link ByteArrayOutputStream}.
	 * 
	 * @param inputStream The {@link InputStream} to be read from.
	 * @param removeReturn Whether or not the stream return (\r\n) should be removed from the line.
	 * @return The content of the line as a {@link ByteArrayOutputStream}.
	 * @throws IOException If there is a read failure.
	 */
	private static ByteArrayOutputStream readLineToStream(InputStream inputStream, boolean removeReturn) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    boolean store = false;
	    int c;
	    for (c = inputStream.read(); c != -1; c = inputStream.read()) {
	    	boolean endAfter = false;
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
	
	/**
	 * Merges two byte arrays of indeterminate length into one (effectively byte1[] + byte2[]).
	 * 
	 * @param byte1 The byte array to be added first.
	 * @param byte2 The byte array to be added second.
	 * @return The merged byte array.
	 */
	public static byte[] joinByteArray(byte[] byte1, byte[] byte2) {
		return joinByteArrayToBuffer(byte1, byte2).array();
	}
	
	/**
	 * Merges two byte arrays of indeterminate length into a {@link ByteBuffer}
	 * 
	 * @param byte1 The byte array to be added first.
	 * @param byte2 The byte array to be added second.
	 * @return The merged {@link ByteBuffer}
	 */
	public static ByteBuffer joinByteArrayToBuffer(byte[] byte1, byte[] byte2) {
		return ByteBuffer.allocate(byte1.length + byte2.length)
	            .put(byte1)
	            .put(byte2);
	}
	
	/**
	 * Merges two byte arrays of indeterminate length into one byte[] with a trailing return (\r\n).
	 * 
	 * @param byte1 The byte array to be added first.
	 * @param byte2 The byte array to be added second.
	 * @return The merged byte array with trailing return (\r\n).
	 */
	public static byte[] joinByteArrayWithNewLine(byte[] byte1, byte[] byte2) {
		return ByteBuffer.allocate(byte1.length + byte2.length + 2)
	            .put(byte1)
	            .put(byte2)
	            .put((byte) '\r')
	            .put((byte) '\n')
	            .array();
	}
	
}
