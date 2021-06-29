package net.aionstudios.proteus.request;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.fileupload.FileItem;

public class MultipartFile {
	
	private FileItem file;
	
	/**
	 * Creates a new Multipart File object to be used by {@link ElementProcessor}s.
	 * @param fieldName The name of the form field that exposed this file upload.
	 * @param fileName The name, from the client, of this file.
	 * @param contentType The content type of this file.
	 * @param inputStream An active input stream which can be used to read this file to active or physical memory.
	 * @param size The size, in bytes, of this file.
	 */
	public MultipartFile(FileItem file) {
		this.file = file;
	}

	/**
	 * @return The name of the form field that exposed this file upload.
	 */
	public String getFieldName() {
		return file.getFieldName();
	}
	
	/**
	 * @return The name, from the client, of this file.
	 */
	public String getFileName() {
		return file.getName();
	}

	/**
	 * @return The content type of this file.
	 */
	public String getContentType() {
		return file.getContentType();
	}

	/**
	 * @return An active input stream which can be used to read this file to active or physical memory.
	 */
	public InputStream getInputStream() {
		try {
			return file.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return The size, in bytes, of this file.
	 */
	public long getSize() {
		return file.getSize();
	}
	
	public void delete() {
		file.delete();
	}
	
}
