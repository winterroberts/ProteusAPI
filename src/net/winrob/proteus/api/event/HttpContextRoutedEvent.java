package net.winrob.proteus.api.event;

import net.winrob.commons.saon.Event;
import net.winrob.proteus.api.context.ProteusHttpContext;
import net.winrob.proteus.routing.HttpRoute;

public abstract class HttpContextRoutedEvent extends Event {
	
	public abstract ProteusHttpContext getContext();
	
	public abstract HttpRoute getRoute();

}
