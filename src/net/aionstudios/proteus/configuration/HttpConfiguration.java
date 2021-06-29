package net.aionstudios.proteus.configuration;

import javax.net.ssl.SSLContext;

import net.aionstudios.proteus.api.context.ProteusHttpContext;

public class HttpConfiguration extends EndpointConfiguration {
	
	private boolean enableBrotli;
	
	private ContextController<ProteusHttpContext> contextController;
	
	public HttpConfiguration(int port, boolean enableBrotli, ProteusHttpContext... contexts) {
		this(port, enableBrotli, null, contexts);
	}
	
	public HttpConfiguration(int port, boolean enableBrotli, SSLContext sslContext, ProteusHttpContext... contexts) {
		super(port, sslContext);
		this.enableBrotli = enableBrotli;
		contextController = ContextController.newInstance(contexts);
	}

	public boolean isEnableBrotli() {
		return enableBrotli;
	}

	@Override
	public ContextController<ProteusHttpContext> getContextController() {
		return contextController;
	}

}
