package net.aionstudios.proteus.api;

import net.aionstudios.proteus.routing.Router;

public abstract class ProteusImplementer {
	
	public ProteusImplementer() {
		// TODO console like intelligent tab completer (component diamond interface)
		// TODO Json serializable classes with @ interface. declare all in class @ and require getters and setters for each labeled as well. Must support empty constructor
			// Implement <> interface?
	}
	
	public abstract Router onEnable();
	
	// No post enable, plugin hook event can be handled as needed.
	
	public abstract void onDisable();

}
