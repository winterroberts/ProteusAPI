package net.aionstudios.proteus.api.event;

import net.aionstudios.proteus.api.ProteusImplementer;

/**
 * An abstraction which defines a consistent pattern for creating and and firing events
 * controlled by the {@link ProteusAPI} or created by plugins (which may have dependencies).
 * 
 * TODO: In a future release Events will be able to target reception by specific plugins rather
 * than the whole application.
 * 
 * @author Winter Roberts
 *
 */
public abstract class Event {
	
	private boolean cancelled = false;
	private boolean cancellable = true;
	private final ProteusImplementer originator;
	
	/**
	 * Constructs this event spawned from the provided {@link ProteusImplementer} which,
	 * by default, is cancellable.
	 * 
	 * @param originator The {@link ProteusImplementer} which spawned this event.
	 */
	public Event(ProteusImplementer originator) {
		this.originator = originator;
	}
	
	/**
	 * Constructs this event spawn from the provided {@link ProteusImplementer} which may
	 * or may not be cancellable.
	 * 
	 * @param originator The {@link ProteusImplementer} which spawned this event.
	 * @param cancellable A boolean indication whether or not this event can be cancelled during propagation.
	 */
	public Event(ProteusImplementer originator, boolean cancellable) {
		this(originator);
		this.cancellable = cancellable;
	}
	
	/**
	 * Fires this event.
	 * 
	 * @return True if the default behavior is completed successfully (no error, not cancelled if it can be).
	 */
	protected abstract boolean fire();

	/**
	 * @return True if and only if the event is cancellable and has been cancelled by an {@link EventHandler} which
	 * is registered to this event.
	 */
	public final boolean isCancelled() {
		return cancellable ? cancelled : false;
	}

	/**
	 * @return True if this event can be cancelled during propagation, false otherwise.
	 */
	public final boolean isCancellable() {
		return cancellable;
	}

	/**
	 * @return The {@link ProteusImplementer} that spawned this event.
	 */
	public final ProteusImplementer getOriginator() {
		return originator;
	}
	
	/**
	 * Cancels this event (this has no effect if the event is not cancellable).
	 */
	public final void cancel() {
		cancelled = true;
	}

}
