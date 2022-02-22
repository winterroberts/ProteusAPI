package net.aionstudios.proteus.api;

import net.aionstudios.proteus.routing.Router;

/**
 * The base plugin class.
 * Each plugin or website must provide its own implementer which may register events
 * or 
 * 
 * @author Winter Roberts
 *
 */
public abstract class ProteusImplementer {
	
	/**
	 * Constructs a new ProteusImplementer.
	 * The class is constructed with an {@link ObjectFactor} which prevents this from running.
	 */
	public ProteusImplementer() {
		// TODO console support and tab completion.
		// In the future this will be a JSON serializable class.
		// Implement <> interface?
	}
	
	/**
	 * Effectively the constructor for this plugin/website.
	 * Event hooks should be registered in this method.
	 * 
	 * The {@link Router} returned by this object will tell the server where
	 * to send each request.
	 * 
	 * @return A Router, which may be null for plugins.
	 */
	public abstract Router onEnable();
	
	// No post enable, plugin hook event can be handled by classes that require each other.
	
	/**
	 * Effectively a destructor for this plugin/website.
	 * This is intend to function as a shutdown hook, saving
	 * any in-memory data.
	 * 
	 * NOTE: Events and routers do not need to be unregistered or reset.
	 */
	public abstract void onDisable();

}
