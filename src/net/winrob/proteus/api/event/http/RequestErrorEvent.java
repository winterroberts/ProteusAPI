package net.winrob.proteus.api.event.http;

import java.io.OutputStream;

import net.winrob.commons.saon.Event;
import net.winrob.proteus.api.response.ResponseCode;

public abstract class RequestErrorEvent extends Event {
	
	public RequestErrorEvent(boolean cancellable) {
		super(cancellable);
	}
	
	public abstract ResponseCode getResponseCode();
	
	public abstract OutputStream getOutputStream();

}
