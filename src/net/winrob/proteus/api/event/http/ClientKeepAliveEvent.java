package net.winrob.proteus.api.event.http;

import java.net.Socket;

import net.winrob.commons.saon.Event;
import net.winrob.proteus.routing.CompositeRouter;

public abstract class ClientKeepAliveEvent extends Event {
	
	public abstract Socket getClientSocket();
	
	public abstract CompositeRouter getRouter();

}
