package net.aionstudios.proteus.api.event;

public class EventListener {

	public EventListener() {
		EventManager.getInstance().submitEventListener(this);
	}
	
}
