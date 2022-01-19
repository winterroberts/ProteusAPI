package net.aionstudios.proteus.request;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import net.aionstudios.proteus.api.context.ProteusWebSocketContext;
import net.aionstudios.proteus.api.util.RequestUtils;
import net.aionstudios.proteus.header.ProteusHttpHeaders;
import net.aionstudios.proteus.routing.CompositeRouter;
import net.aionstudios.proteus.routing.Hostname;
import net.aionstudios.proteus.routing.PathComprehension;
import net.aionstudios.proteus.routing.Router;
import net.aionstudios.proteus.routing.WebSocketRoute;

public class ProteusWebSocketRequest {
	
	private Hostname hostname;
	
	private ProteusHttpHeaders headers;
	
	private RequestBody body;
	private ParameterMap<String> urlParameters;
	private ParameterMap<String> cookies;
	
	private WebSocketRoute route;
	
	public ProteusWebSocketRequest(Socket client, String path, String host, ProteusHttpHeaders headers, CompositeRouter router) {
		this.hostname = new Hostname(host);
		this.headers = headers;
		route = router.getWebSocketRoute(hostname, resolveURI(path));
		cookies = headers.hasHeader("Cookie") ? headers.getHeader("Cookie").getFirst().getParams() : null;
	}
	
	private String resolveURI(String path) {
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
		urlParameters = new ParameterMap<>(getP);
		return requestSplit[0];
	}
	
	public Hostname getHostname() {
		return hostname;
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

	public ProteusWebSocketContext getContext() {
		return route != null ? route.getContext() : null;
	}
	
	public PathComprehension getPathComprehension() {
		return route != null ? route.getPathComprehension() : null;
	}
	
	public boolean routed() {
		return route != null;
	}
	
}