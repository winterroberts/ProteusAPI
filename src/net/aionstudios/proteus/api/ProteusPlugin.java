package net.aionstudios.proteus.api;

import net.winrob.commons.saon.EventDispatcher;

/**
 * The base plugin class.
 * 
 * @author Winter Roberts
 *
 */
public abstract class ProteusPlugin {
	
	/**
	 * Constructs a new ProteusPlugin.
	 * The class is constructed with an {@link ObjectFactor} which prevents this from running.
	 */
	public ProteusPlugin() {
		// TODO console support and tab completion.
		// In the future this will be a JSON serializable class.
		// Implement <> interface?
	}
	
	/**
	 * Effectively the constructor for this plugin.
	 * Event hooks should be registered in this method.
	 * 
	 * @param dispatcher To register event listeners for server events
	 */
	public abstract void onEnable(EventDispatcher dispatcher);
	
	
	// No post enable, plugin hook event can be handled by classes that require each other.
	
	/**
	 * Effectively a destructor for this plugin.
	 * This is intend to function as a shutdown hook, saving
	 * any in-memory data.
	 * 
	 * NOTE: Events and routers do not need to be unregistered or reset.
	 */
	public abstract void onDisable();

}
