package net.winrob.proteus.api.event;

import java.net.ServerSocket;

import net.winrob.commons.saon.Event;
import net.winrob.proteus.routing.CompositeRouter;

public class ListenerThreadSpawnedEvent extends Event {
	
	private ServerSocket serverSocket;
	private CompositeRouter router;
	
	public ListenerThreadSpawnedEvent(ServerSocket serverSocket, CompositeRouter router) {
		this.serverSocket = serverSocket;
		this.router = router;
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	
	public CompositeRouter getRouter() {
		return router;
	}

	@Override
	protected boolean run() {
		return true;
	}

}
