package net.aionstudios.proteus.configuration;

public enum EndpointType {
	
	HTTP(true, false),
	WEBSOCKET(false, true),
	MIXED(true, true);
	
	private boolean http;
	private boolean webSocket;
	
	private EndpointType(boolean http, boolean webSocket) {
		this.http = http;
		this.webSocket = webSocket;
	}
	
	public String toString() {
		return http ? (webSocket ? "mixed traffic" : "http") : (webSocket ? "web socket" : "ERRANT");
	}
	
	public boolean isHttp() {
		return http;
	}
	
	public boolean isWebSocket() {
		return webSocket;
	}

}
