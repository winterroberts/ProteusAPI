package net.aionstudios.proteus.request;

import java.io.IOException;
import java.io.InputStream;

public class MultipartFileStream extends InputStream {
	
	private InputStream content;
	
	private String fieldName;
	private String fileName;
	private String contentType;
	private int size;
	
	private int read;
	
	private boolean deleted;
	
	/**
	 * Creates a new Multipart File object to be used by {@link ElementProcessor}s.
	 * @param fieldName The name of the form field that exposed this file upload.
	 * @param fileName The name, from the client, of this file.
	 * @param contentType The content type of this file.
	 * @param inputStream An active input stream which can be used to read this file to active or physical memory.
	 * @param size The size, in bytes, of this file.
	 */
	public MultipartFileStream(InputStream content, String fieldName, String fileName, String contentType, int size) {
		this.content = content;
		this.fieldName = fieldName;
		this.fileName = fileName;
		this.contentType = contentType;
		this.size = size;
		deleted = false;
	}

	/**
	 * @return The name of the form field that exposed this file upload.
	 */
	public String getFieldName() {
		return fieldName;
	}
	
	/**
	 * @return The name, from the client, of this file.
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return The content type of this file.
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @return The size, in bytes, of this file.
	 */
	public int getSize() {
		return size;
	}
	
	public boolean delete() {
		if (!deleted) {
			try {
				this.readAllBytes();
			} catch (IOException e) {
				e.printStackTrace();
			}
			deleted = true;
		}
		return deleted;
	}

	@Override
	public int read() throws IOException {
		if (read < size) {
			read++;
			return content.read();
		}
		deleted = true;
		return -1;
	}
	
}
