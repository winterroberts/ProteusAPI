package net.aionstudios.proteus.api.event;

import net.aionstudios.proteus.api.context.ProteusHttpContext;
import net.aionstudios.proteus.routing.HttpRoute;
import net.winrob.commons.saon.Event;

public abstract class HttpContextRoutedEvent extends Event {
	
	public abstract ProteusHttpContext getContext();
	
	public abstract HttpRoute getRoute();

}
