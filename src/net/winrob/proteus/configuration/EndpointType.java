package net.winrob.proteus.configuration;

/**
 * An enumeration of the endpoint protocol combinations on the server.
 * 
 * @author Winter Roberts
 *
 */
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
	
	/**
	 * @return True if this endpoint type indicates HTTP support, false otherwise.
	 */
	public boolean isHttp() {
		return http;
	}
	
	/**
	 * @return True if this endpoint type indicates WebSocket support, false otherwise.
	 */
	public boolean isWebSocket() {
		return webSocket;
	}

}
