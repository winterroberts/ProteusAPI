package net.aionstudios.proteus.api.context;

import net.aionstudios.proteus.request.ProteusWebSocketConnection;
import net.aionstudios.proteus.request.WebSocketBuffer;
import net.aionstudios.proteus.routing.PathInterpreter;

@ProteusContext(path="/")
public abstract class ProteusWebSocketContext {
	
	private ProteusContext useContext;
	private PathInterpreter[] paths;
	
	public final PathInterpreter[] getPaths() {
		getContext();
		return paths;
	}
	
	private final ProteusContext getContext() {
		if (useContext==null) {
			ProteusContext pc = this.getClass().getAnnotation(ProteusContext.class);
			ProteusContext pct = this.getClass().getAnnotatedSuperclass().getAnnotation(ProteusContext.class);
			if (pct != null && pct.preserveType()) {
				paths = new PathInterpreter[pct.path().length + pc.path().length];
				for (int i = 0; i < pct.path().length; i++) {
					paths[i] = new PathInterpreter(pct.path()[i]);
				}
				for (int i = 0; i < pc.path().length; i++) {
					paths[i+pct.path().length] = new PathInterpreter(pc.path()[i]);
				}
			} else if (pct != null) {
				paths = new PathInterpreter[pct.path().length];
				String[] pctp = pct.path();
				for (int i = 0; i < pctp.length; i++) {
					paths[i] = new PathInterpreter(pctp[i]);
				}
			} else if (pc != null) {
				paths = new PathInterpreter[pc.path().length];
				String[] pcp = pc.path();
				for (int i = 0; i < pcp.length; i++) {
					paths[i] = new PathInterpreter(pcp[i]);
				}
			} else {
				paths = null;
			}
			useContext = pct != null ? pct : pc;
		}
		return useContext;
	}
	
	public final boolean pathMatch(String path) {
		getContext();
		for (PathInterpreter it : paths) {
			if (it.matches(path)) {
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
