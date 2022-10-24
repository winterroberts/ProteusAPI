package net.aionstudios.proteus.api.request;

import java.io.InputStream;

/**
 * Handles HTTP file upload as an {@link InputStream}
 * 
 * @author Winter Roberts
 *
 */
public interface MultipartFileStream {

	/**
	 * @return The name of the form field that exposed this file upload.
	 */
	public String getFieldName();
	
	/**
	 * @return The name, from the client, of this file.
	 */
	public String getFileName();

	/**
	 * @return The content type of this file.
	 */
	public String getContentType();

	/**
	 * @return The size, in bytes, of this file.
	 */
	public int getSize();
	
	/**
	 * @return Reads this stream without storing the contents.
	 */
	public boolean delete();
	
}
