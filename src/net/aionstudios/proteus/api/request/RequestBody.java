package net.aionstudios.proteus.api.request;

/**
 * The body information, part of the {@link ProteusHttpRequest}.
 * 
 * @author Winter Roberts
 *
 */
public interface RequestBody {
	
	/**
	 * @return A {@link ParameterMap<String>}, the body parameters of this request, if any.
	 */
	public ParameterMap<String> getBodyParams();
	
	/**
	 * @return A {@link ParameterMap<MultipartFileStream>}, the payload body of this request, if any.
	 */
	public ParameterMap<MultipartFileStream> getFiles();
	
	/**
	 * @return The declared content type of the request body.
	 */
	public String getContentType();
	
	/**
	 * @return The raw text value (if the body is raw text) of the body.
	 */
	public String getRawText();
	
	/**
	 * @return The {@link MultipartFileStream} of the body if a the payload was a file.
	 */
	public MultipartFileStream getRawFile();
	
}
