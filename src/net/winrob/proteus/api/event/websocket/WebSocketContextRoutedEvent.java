package net.winrob.proteus.api.event.websocket;

import net.winrob.commons.saon.Event;
import net.winrob.proteus.api.context.ProteusWebSocketContext;
import net.winrob.proteus.api.request.ProteusWebSocketConnection;
import net.winrob.proteus.routing.WebSocketRoute;

public abstract class WebSocketContextRoutedEvent extends Event {
	
	public abstract ProteusWebSocketConnection getConnection();
	
	public abstract ProteusWebSocketContext getContext();
	
	public abstract WebSocketRoute getRoute();

}
