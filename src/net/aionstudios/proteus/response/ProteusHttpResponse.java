package net.aionstudios.proteus.response;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import net.aionstudios.proteus.api.util.FormatUtils;
import net.aionstudios.proteus.compression.CompressionEncoding;
import net.aionstudios.proteus.compression.Compressor;
import net.aionstudios.proteus.header.ProteusHeader;
import net.aionstudios.proteus.header.ProteusHeaderBuilder;
import net.aionstudios.proteus.header.ProteusHttpHeaders;
import net.aionstudios.proteus.request.ProteusHttpRequest;

public class ProteusHttpResponse {
	
	private OutputStream outputStream;
	private CompressionEncoding encoding;
	
	private ProteusHeaderBuilder headerBuilder;
	
	private String mimeString;
	private Long modified = null;
	
	// TODO cookies, special headers
	// TODO range request (which will be in an implementer...) but file support may also be native for pass-through.
	private boolean complete = false;
	
	public ProteusHttpResponse(OutputStream outputStream, CompressionEncoding encoding) {
		this.outputStream = outputStream;
		this.encoding = encoding;
		this.mimeString = "text/html";
		headerBuilder = new ProteusHeaderBuilder();
	}
	
	public void sendResponse(byte[] response) {
		sendResponse(ResponseCode.OK, response);
	}
	
	public void sendResponse(String response) {
		sendResponse(ResponseCode.OK, response);
	}
	
	public void sendResponse(ResponseCode responseCode, String response) {
		sendResponse(responseCode, response.getBytes());
	}
	
	public void sendResponse(ResponseCode responseCode, byte[] response) {
		if (!complete) {
			complete = true;
			headerBuilder.putHeader("Content-Type", mimeString);
			headerBuilder.putHeader("Last-Modified", FormatUtils.getLastModifiedAsHTTPString(modified != null ? modified : System.currentTimeMillis()));
			if (encoding != CompressionEncoding.NONE) {
				headerBuilder.putHeader("Content-Encoding", encoding.getName());
			}
			// TODO support for header injection
			try {
				byte[] respBytes = Compressor.compress(response, encoding);
				sendResponseHeaders(responseCode, respBytes.length);
				outputStream.write(respBytes);
				outputStream.write("\r\n\r\n".getBytes());
				safeCloseStream(outputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void sendResponseHeaders(ResponseCode responseCode, int length) throws IOException {
		outputStream.write(("HTTP/1.1 " + responseCode.getCode() + " " + responseCode.getName() + "\r\n").getBytes());
		outputStream.write(("Content-Length: " + length + "\r\n").getBytes());
        for (String header : headerBuilder.toList()) {
        	outputStream.write((header + "\r\n").getBytes());
        }
        outputStream.write("\r\n".getBytes());
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

}
