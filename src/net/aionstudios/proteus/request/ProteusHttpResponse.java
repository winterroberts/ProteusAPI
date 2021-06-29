package net.aionstudios.proteus.request;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import net.aionstudios.proteus.api.util.FormatUtils;
import net.aionstudios.proteus.compression.CompressionEncoding;
import net.aionstudios.proteus.compression.Compressor;
import net.aionstudios.proteus.response.ResponseCode;

public class ProteusHttpResponse {
	
	private static Map<ProteusHttpRequest, ProteusHttpResponse> responseMap;
	
	private ProteusHttpRequest request;
	
	private String mimeString = "text/html";
	private Long modified = null;
	
	// TODO cookies, special headers
	// TODO range request (which will be in an implementer...) but file support may also be native for pass-through.
	private boolean complete = false;
	
	private ProteusHttpResponse(ProteusHttpRequest request) {
		this.request = request;
	}
	
	public void sendResponse(byte[] response) {
		sendResponse(ResponseCode.OK, response);
	}
	
	public void sendResponse(ResponseCode responseCode, byte[] response) {
		sendResponse(responseCode.getCode(), response);
	}
	
	public void sendResponse(String response) {
		sendResponse(ResponseCode.OK, response);
	}
	
	public void sendResponse(ResponseCode responseCode, String response) {
		sendResponse(responseCode.getCode(), response);
	}
	
	public void sendResponse(int code, String response) {
		sendResponse(code, response.getBytes());
	}
	
	public void sendResponse(int code, byte[] response) {
		if (!complete) {
			complete = true;
			HttpExchange exchange = request.getHttpExchange();
			Headers respHeaders = exchange.getResponseHeaders();
			respHeaders.set("Content-Type", mimeString);
			respHeaders.set("Last-Modified", FormatUtils.getLastModifiedAsHTTPString(modified != null ? modified : System.currentTimeMillis()));
			CompressionEncoding responseCompression = request.getCompressionEncoding();
			if (responseCompression != CompressionEncoding.NONE) {
				respHeaders.set("Content-Encoding", responseCompression.getName());
			}
			// TODO support for header injection
			try {
				byte[] respBytes = Compressor.compress(response, responseCompression);
				exchange.sendResponseHeaders(code, respBytes.length);
				OutputStream os = exchange.getResponseBody();
				os.write(respBytes);
				safeCloseStream(os);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Closes the given stream safely to prevent errors in data integrity and thread crashes.
	 * @param os	The output stream to be closed.
	 */
	private static void safeCloseStream(OutputStream os) {
		try {
			os.flush();
		} catch (Exception e) {
			//ignore
		}
		try {
			os.close();
		} catch (Exception e) {
			//ignore
		}
	}
	
	public void setModified(long time) {
		if (!complete) {
			modified = time;
		}
	}
	
	public void setMimeString(String mime) {
		if (!complete) {
			mimeString = mime;
		}
	}
	
	public static ProteusHttpResponse getResponserForRequest(ProteusHttpRequest request) {
		if (responseMap == null) responseMap = new HashMap<>();
		if (request == null) return null;
		if (!responseMap.containsKey(request)) {
			responseMap.put(request, new ProteusHttpResponse(request));
		}
		return responseMap.get(request);
	}

}
