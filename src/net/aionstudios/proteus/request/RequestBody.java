package net.aionstudios.proteus.request;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import net.aionstudios.proteus.api.util.StreamUtils;
import net.aionstudios.proteus.header.HeaderValue;
import net.aionstudios.proteus.header.ProteusHeaderBuilder;
import net.aionstudios.proteus.header.ProteusHttpHeaders;

public class RequestBody {
	
	private ParameterMap<String> bodyData;
	private ParameterMap<MultipartFileStream> fileData;
	
	private boolean resume;

	private RequestBody() {
		bodyData = new ParameterMap<>();
		fileData = new ParameterMap<>();
		resume = false;
	}
	
	public ParameterMap<String> getBodyParams() {
		return bodyData;
	}
	
	public ParameterMap<MultipartFileStream> getFiles() {
		return fileData;
	}
	
	public static RequestBody createRequestBody(ProteusHttpRequest request, InputStream inputStream) {
		RequestBody body = new RequestBody();
		ProteusHttpHeaders headers = request.getHeaders();
		if (headers.hasHeader("Content-Type")) {
			if (headers.hasHeader("Content-Length")) {
				HeaderValue contentType = headers.getHeader("Content-Type").getLast();
				int contentLength = Integer.parseInt(headers.getHeader("Content-Length").getLast().getValue());
				switch(contentType.getValue()) {
				case "application/x-www-form-urlencoded":
					body.contentFormUrlEncoded(contentLength, inputStream);
					break;
				case "multipart/form-data":
					body.contentFormData(inputStream, contentType, null);
					break;
				default:
					// TODO Error on bad headers
				}
			} else {
				// TODO Error on missing length
				return null;
			}
		}
		return body;
	}
	
	private boolean contentFormUrlEncoded(int length, InputStream in) {
		try {
			byte[] data = in.readNBytes(length);
			String kvs = new String(data, StandardCharsets.UTF_8);
			for (String kvt : kvs.split("&")) {
				String[] keyValue = kvt.split("=");
				if (keyValue.length != 2) {
					continue;
				}
				bodyData.putParameter(keyValue[0], URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8));
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean contentFormData(InputStream in, HeaderValue contentType, String retainName) {
		try {
			if (contentType.getParams().hasParameter("boundary")) {
				String boundary = contentType.getParams().getParameter("boundary");
				String boundaryStart = "--" + boundary;
				String boundaryEnd = boundaryStart + "--";
				boolean flag = false;
				while (!flag) {
					String boundarySeek = StreamUtils.readLine(in, true);
					if (boundarySeek.equals(boundaryStart)) {
						flag = true;
						resume = true;
					} else if (boundarySeek.equals(boundaryEnd)) {
						flag = true;
					}
				}
				while (resume) {
					flag = false;
					resume = false;
					StringBuilder contentBuilder = new StringBuilder();
			        String line;
			        while (!(line = StreamUtils.readLine(in, true)).isBlank()) {
			        	contentBuilder.append(line + "\r\n");
			        }
			        
			        String[] contentLines = contentBuilder.toString().split("\r\n");
			        
					ProteusHeaderBuilder headerBuilder = new ProteusHeaderBuilder();
			        for (int h = 0; h < contentLines.length; h++) {
			        	String header = contentLines[h];
			        	String[] headerSplit = header.split(":", 2);
			        	if (headerSplit.length == 2 && headerSplit[0].length() > 0 && headerSplit[1].length() > 0) {
			        		headerBuilder.putHeader(headerSplit[0].trim(), headerSplit[1].trim());
			        	} else {
			        		// TODO error
			        	}
			        }
			        ProteusHttpHeaders headers = headerBuilder.toHeaders();
			        
			        if (headers.hasHeader("Content-Disposition")) {
			        	HeaderValue disposition = headers.getHeader("Content-Disposition").getLast();
			        	String name = getOrRetainName(retainName, disposition);
			        	if (disposition.getValue().equals("form-data")) {
			        		if (disposition.getParams().hasParameter("filename")) {
			        			String filename = trimQuotes(disposition.getParams().getParameter("filename"));
			        			handleFile(headers, name, filename, in, boundaryStart, boundaryEnd);
			        		} else {
			        			if (name != null) {
			        				if (headers.hasHeader("Content-Type")) {
				        				contentFormData(in, headers.getHeader("Content-Type").getLast(), name);
				        			} else {
				        				String value = readParamToBoundary(in, boundaryStart, boundaryEnd);
				        				bodyData.putParameter(name, value);
				        			}
			        			}
			        		}
			        	} else if (disposition.getValue().equals("file") || disposition.getValue().equals("attachment")) {
			        		if (disposition.getParams().hasParameter("filename")) {
			        			String filename = trimQuotes(disposition.getParams().getParameter("filename"));
			        			handleFile(headers, name, filename, in, boundaryStart, boundaryEnd);
			        		}
			        	} else {
			        		// TODO error
			        	}
			        } else {
			        	// TODO error
			        }
				}
			} else {
				// TODO error
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private String getOrRetainName(String retain, HeaderValue header) {
		String name = null;
		if (retain != null) {
			name = retain;
		}
		if (header.getParams().hasParameter("name")) {
			name = trimQuotes(header.getParams().getParameter("name"));
		}
		return name;
	}
	
	private boolean handleFile(ProteusHttpHeaders headers, String name, String filename, InputStream inputStream, String boundaryStart, String boundaryEnd) throws IOException {
		if (name != null) {
			String contentType = headers.getHeader("Content-Type").getLast().getValue();
			boolean useCl = false;
			int contentLength = 0;
			if (headers.hasHeader("Content-Length")) {
				contentLength = Integer.parseInt(headers.getHeader("Content-Length").getLast().getValue());
				useCl = true;
			}
			byte[] bytes = readFileToBoundary(useCl, contentLength, inputStream, boundaryStart, boundaryEnd);
			fileData.putParameter(name, new MultipartFileStream(new ByteArrayInputStream(bytes), name, filename, contentType, bytes.length));
		}
		return false;
	}
	
	private String readParamToBoundary(InputStream in, String boundaryStart, String boundaryEnd) throws IOException {
		StringBuilder reply = new StringBuilder();
		String line = StreamUtils.readLine(in, true);
		boolean lineAhead = false;
		while (lineAhead || (!line.equals(boundaryStart) && !line.equals(boundaryEnd))) {
			lineAhead = false;
			reply.append(line);
			line = StreamUtils.readLine(in, true);
			if (!line.equals(boundaryStart) && !line.equals(boundaryEnd)) {
				reply.append("\r\n");
				lineAhead = true;
			}
		}
		if (line.equals(boundaryStart)) {
			resume = true;
		}
		return reply.toString();
	}
	
	private byte[] readFileToBoundary(boolean useContentLength, int length, InputStream in, String boundaryStart, String boundaryEnd) throws IOException {
		if (!useContentLength) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] byteLine = StreamUtils.readRawLine(in, true);
			String line = new String(byteLine, StandardCharsets.UTF_8);
			boolean lineAhead = false;
			while (lineAhead || (!line.equals(boundaryStart) && !line.equals(boundaryEnd))) {
				lineAhead = false;
				baos.write(byteLine);
				byteLine = StreamUtils.readRawLine(in, true);
				line = new String(byteLine, StandardCharsets.UTF_8);
				if (!line.equals(boundaryStart) && !line.equals(boundaryEnd)) {
					baos.write('\r');
					baos.write('\n');
					lineAhead = true;
				}
			}
			if (line.equals(boundaryStart)) {
				resume = true;
			}
			return baos.toByteArray();
		} else {
			byte[] bytes = in.readNBytes(length);
			String line = StreamUtils.readLine(in, true);
			while (!line.equals(boundaryStart) && !line.equals(boundaryEnd)) {
				line = StreamUtils.readLine(in, true);
			}
			if (line.equals(boundaryStart)) {
				resume = true;
			}
			return bytes;
		}
	}
	
	private String trimQuotes(String before) {
		if (before.startsWith("\"")) {
			before = before.substring(1);
		}
		if (before.endsWith("\"")) {
			before = before.substring(0, before.length() - 1);
		}
		return before;
	}
	
}
