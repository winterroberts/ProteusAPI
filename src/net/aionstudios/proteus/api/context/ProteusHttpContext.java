package net.aionstudios.proteus.api.context;

import net.aionstudios.proteus.request.ProteusHttpRequest;
import net.aionstudios.proteus.response.ProteusHttpResponse;
import net.aionstudios.proteus.routing.PathInterpreter;

@ProteusContext(path="/")
/**
 * An abstraction which accepts traffic for an endpoint (as defined by a {@link Router} and its {@link PathInterpreter}s).
 * 
 * @author Winter Roberts
 *
 */
public abstract class ProteusHttpContext {
	
	private ProteusContext useContext;
	private PathInterpreter[] paths;
	
	/**
	 * @return The list of valid path interpretations that accept traffic for this context.
	 */
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
	
	/**
	 * Determines if any {@link PathInterpreter} associated with this context can build a
	 * comprehension of the given path.
	 * 
	 * @param path The path string (likely from a request)
	 * @return True if this context accepts traffic to the endpoint, false otherwise.
	 */
	public final boolean pathMatch(String path) {
		getContext();
		for (PathInterpreter it : paths) {
			if (it.matches(path)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * A context handler which accepts information about the user request and responds with
	 * data (which may be of any type including application/json, text/html, stream/octet, etc.)
	 * 
	 * @param request The {@link ProteusHttpRequest} which contains the information about this request.
	 * @param response The {@link ProteusHttpResponse} which will be populated with response information.
	 */
	public abstract void handle(ProteusHttpRequest request, ProteusHttpResponse response);

}
