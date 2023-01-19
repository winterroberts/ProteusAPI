package net.winrob.proteus.api.event;

import net.winrob.commons.saon.Event;
import net.winrob.proteus.header.ProteusHttpHeaders;

public abstract class RequestReceivedEvent extends Event {
	
	public abstract ProteusHttpHeaders getHeaders();
	
	public abstract String getMethod();
	
	public abstract String getPath();
	
	public abstract String getVersion();
	
	public abstract boolean isSecure();
	
	public abstract boolean isWebSocket();

}
