package net.aionstudios.proteus.api.context;

import net.aionstudios.proteus.request.ProteusWebSocketConnection;
import net.aionstudios.proteus.request.WebSocketBuffer;

@ProteusContext(path="/")
public abstract class ProteusWebSocketContext {
	
	private ProteusContext useContext;
	private String[] paths;
	private boolean acceptChildren;
	
	public final String[] getPaths() {
		getContext();
		return paths;
	}
	
	private final ProteusContext getContext() {
		if (useContext==null) {
			ProteusContext pc = this.getClass().getAnnotation(ProteusContext.class);
			ProteusContext pct = this.getClass().getAnnotatedSuperclass().getAnnotation(ProteusContext.class);
			if (pct != null && pct.preserveType()) {
				paths = new String[pct.path().length + pc.path().length];
				for (int i = 0; i < pct.path().length; i++) {
					paths[i] = pct.path()[i];
				}
				for (int i = 0; i < pc.path().length; i++) {
					paths[i+pct.path().length] = pc.path()[i];
				}
				acceptChildren = pct.acceptChildren() || pc.acceptChildren();
			} else if (pct != null) {
				paths = pct.path();
				acceptChildren = pct.acceptChildren();
			} else {
				paths = pc.path();
				acceptChildren = pc.acceptChildren();
			}
			useContext = pct != null ? pct : pc;
		}
		return useContext;
	}
	
	public final boolean acceptChildren() {
		getContext();
		return acceptChildren;
	}
	
	public final boolean pathMatch(String path) {
		getContext();
		for (String s : paths) {
			if (path.equals(s) || (acceptChildren() && path.startsWith(s))) {
				return true;
			}
		}
		return false;
	}
	
	// TODO
	public abstract void onOpen(ProteusWebSocketConnection connection);
	
	public abstract void onMessage(ProteusWebSocketConnection connection, WebSocketBuffer message);
	
	public abstract void onClose(ProteusWebSocketConnection connection);
	
	public abstract void onError(ProteusWebSocketConnection connection, Throwable throwable);

}
