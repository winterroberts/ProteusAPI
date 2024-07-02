package net.winrob.proteus.api.event.server;

import javax.net.ssl.SSLSocket;

import net.winrob.commons.saon.Event;
import net.winrob.proteus.routing.CompositeRouter;

public abstract class SSLHandshakeCompletedEvent extends Event {
	
	public abstract SSLSocket getClientSocket();
	
	public abstract CompositeRouter getRouter();

}
