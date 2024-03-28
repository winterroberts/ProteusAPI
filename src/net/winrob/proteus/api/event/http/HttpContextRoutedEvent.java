package net.winrob.proteus.api.event.http;

import net.winrob.commons.saon.Event;
import net.winrob.proteus.api.context.ProteusHttpContext;
import net.winrob.proteus.api.request.ProteusHttpRequest;
import net.winrob.proteus.api.response.ProteusHttpResponse;
import net.winrob.proteus.routing.HttpRoute;

public abstract class HttpContextRoutedEvent extends Event {
	
	public abstract ProteusHttpRequest getRequest();
	
	public abstract ProteusHttpResponse getResponse();
	
	public abstract ProteusHttpContext getContext();
	
	public abstract HttpRoute getRoute();

}
