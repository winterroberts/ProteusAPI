package net.winrob.proteus.api.event;

import net.winrob.commons.saon.Event;
import net.winrob.proteus.api.response.ResponseCode;
import net.winrob.proteus.header.ProteusHeaderBuilder;

public abstract class SendResponseHeadersEvent extends Event {
	
	public abstract ProteusHeaderBuilder getHeaderBuilder();
	
	public abstract ResponseCode getResponseCode();

}
