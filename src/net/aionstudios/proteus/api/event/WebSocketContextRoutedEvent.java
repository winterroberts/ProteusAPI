package net.aionstudios.proteus.api.event;

import net.aionstudios.proteus.api.context.ProteusWebSocketContext;
import net.aionstudios.proteus.routing.WebSocketRoute;
import net.winrob.commons.saon.Event;

public abstract class WebSocketContextRoutedEvent extends Event {
	
	public abstract ProteusWebSocketContext getContext();
	
	public abstract WebSocketRoute getRoute();

}
