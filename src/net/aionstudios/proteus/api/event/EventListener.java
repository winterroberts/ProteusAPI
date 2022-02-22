package net.aionstudios.proteus.api.event;

/**
 * A class which contains any number of methods, which may be annotated with {@link EventHandler}
 * and accept an {@link Event} implementation as their argument.
 * 
 * @author Winter Roberts
 *
 */
public class EventListener {

	/**
	 * Automatically registers this events listener to the {@link EventManager}, which dispatches {@link Event}s.
	 */
	public EventListener() {
		EventManager.getInstance().submitEventListener(this);
	}
	
}
