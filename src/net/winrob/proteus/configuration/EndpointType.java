package net.winrob.proteus.configuration;

/**
 * An enumeration of the endpoint protocol combinations on the server.
 * 
 * @author Winter Roberts
 *
 */
public enum EndpointType {
	
	HTTP1_1("HTTP/1.1"),
	HTTP2("HTTP/2"),
	WEBSOCKET("WebSocket");
	
	private String protocolName;
	
	private EndpointType(String protocolName) {
		this.protocolName = protocolName;
	}
	
	public String getProtocolName() {
		return protocolName;
	}

}
