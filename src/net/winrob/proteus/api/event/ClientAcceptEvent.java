package net.winrob.proteus.api.event;

import java.net.Socket;

import net.winrob.commons.saon.Event;
import net.winrob.proteus.routing.CompositeRouter;

public abstract class ClientAcceptEvent extends Event {
	
	public abstract Socket getClientSocket();
	
	public abstract CompositeRouter getRouter();

}
