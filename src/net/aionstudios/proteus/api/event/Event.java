package net.aionstudios.proteus.api.event;

import net.aionstudios.proteus.api.ProteusImplementer;

public abstract class Event {
	
	private boolean cancelled = false;
	private boolean cancellable = true;
	private final ProteusImplementer originator;
	
	public Event(ProteusImplementer originator) {
		this.originator = originator;
	}
	
	public Event(ProteusImplementer originator, boolean cancellable) {
		this(originator);
		this.cancellable = cancellable;
	}
	
	protected abstract boolean fire();

	public final boolean isCancelled() {
		return cancellable ? cancelled : false;
	}

	public final boolean isCancellable() {
		return cancellable;
	}

	public final ProteusImplementer getOriginator() {
		return originator;
	}
	
	public final void cancel() {
		cancelled = true;
	}

}
