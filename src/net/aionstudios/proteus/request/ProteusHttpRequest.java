package net.aionstudios.proteus.request;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import net.aionstudios.proteus.api.util.RequestUtils;
import net.aionstudios.proteus.header.ProteusHttpHeaders;

public class ProteusHttpRequest {
	
	private InputStream inputStream;
	
	private String remoteAddress;
	
	private String method;
	private String httpVersion;
	private String path;
	private String host;
	
	private ProteusHttpHeaders headers;
	
	private RequestBody body;
	private ParameterMap<String> urlParameters;
	private ParameterMap<String> cookies;
	
	public ProteusHttpRequest(Socket client, String method, String httpVersion, String path, String host, ProteusHttpHeaders headers) {
		try {
			this.inputStream = client.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.remoteAddress = client.getInetAddress().toString();
		this.method = method;
		this.httpVersion = httpVersion;
		this.host = host;
		this.headers = headers;
		resolveURI(path);
		if (method.equals("POST")) {
			body = RequestBody.createRequestBody(this, inputStream);
		}
		cookies = headers.hasHeader("Cookie") ? headers.getHeader("Cookie").getFirst().getParams() : null;
		if (body != null) {
			ParameterMap<String> post = body.getBodyParams();
			for (String s : post.keySet()) {
				System.out.println(s + ": " + post.getParameter(s));
			}
		}
	}
	
	private void resolveURI(String path) {
		String[] requestSplit;
		if(path.contains("?")) {
			requestSplit = path.split("\\?", 2);
		} else {
			requestSplit = new String[2];
			requestSplit[0] = path.toString();
			requestSplit[1] = "";
		}
		Map<String, String> getP = new HashMap<String, String>();
		if(requestSplit.length>1) {
			getP = RequestUtils.resolveQueryString(requestSplit[1]);
		}
		this.path = requestSplit[0];
		urlParameters = new ParameterMap<>(getP);
	}

	public String getMethod() {
		return method;
	}
	
	public String getHttpVersion() {
		return httpVersion;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getHost() {
		return host;
	}

	public ParameterMap<String> getUrlParameters() {
		return urlParameters;
	}

	public RequestBody getRequestBody() {
		return body;
	}

	public ParameterMap<String> getCookies() {
		return cookies;
	}
	
	public ProteusHttpHeaders getHeaders() {
		return headers;
	}
	
	public String getRemoteAddress() {
		return remoteAddress;
	}

}
