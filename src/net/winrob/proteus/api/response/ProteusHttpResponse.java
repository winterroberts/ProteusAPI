package net.winrob.proteus.api.response;

import java.io.InputStream;

import net.winrob.proteus.header.ProteusHeaderBuilder;

/**
 * Used to stage header and body information which will be returned to the client
 * 
 * @author Winter Roberts
 *
 */
public interface ProteusHttpResponse {
	
	/**
	 * Sends the response data (as bytes) with the 200 (OK) status code.
	 * 
	 * @param response The response data.
	 */
	public void sendResponse(byte[] response);
	
	public void sendResponse(InputStream response);
	
	/**
	 * Sends the response string with the 200 (OK) status code.
	 * 
	 * @param response The response string.
	 */
	public void sendResponse(String response);
	
	/**
	 * Sends the response string with the given response code.
	 * 
	 * @param responseCode The {@link ResponeCode} indicating the state of the request.
	 * @param response The response string.
	 */
	public void sendResponse(ResponseCode responseCode, String response);
	
	/**
	 * Sends the response data (as bytes) with the given response code.
	 * 
	 * @param responseCode The {@link ResponeCode} indicating the state of the request.
	 * @param response The response data.
	 */
	public void sendResponse(ResponseCode responseCode, byte[] response);
	
	public void sendResponse(ResponseCode responseCode, InputStream response);
	
	public void sendResponse(ResponseCode responseCode, InputStream response, boolean ignoreCompressionDirective);
	
	public void error(ResponseCode code);
	
	public ProteusHeaderBuilder getHeaderBuilder();
	
	/**
	 * Sets the "Last-Modified" time of the resource, which may be now or last edit.
	 * @param time
	 */
	public void setModified(long time);
	
	/**
	 * Sets the mime type of the resource.
	 * @param mime The mime type as a string.
	 */
	public void setMimeString(String mime);

}
