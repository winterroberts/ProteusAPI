package net.aionstudios.proteus.api.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Keeps track of {@link EventListener}s with {@link EventHandler}s that accept
 * each different {@link Event} implementation.
 * 
 * @author Winter Roberts
 *
 */
public class EventManager {
	
	private static EventManager self;
	private static HashMap<Class<Event>, HashMap<EventListener, Set<Method>>> listeners;
	
	private EventManager() {
		self = this;
	}
	
	/**
	 * @return The singleton instance of this class, which may be constructed if it has not been already.
	 */
	public static EventManager getInstance() {
		return self != null ? self : new EventManager();
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Registers each {@link EventHandler} annotated methods from the given {@link EventListener} to this event manager.
	 * @param listener The {@link EventListener} which contains {@link EventHandler} annotated methods.
	 */
	public final void submitEventListener(EventListener listener) {
		Method[] methods = listener.getClass().getMethods();
		for (Method method : methods) {
			if (method.getAnnotation((Class<EventHandler>) EventHandler.class) != null && method.getParameterTypes().length == 1) {
				// EventHandler eh = (EventHandler) method.getAnnotation((Class<EventHandler>) EventHandler.class);
				Class<?> e = method.getParameterTypes()[0];
				if (e.isAssignableFrom(Event.class)) {
					if (!listeners.containsKey(e)) {
						listeners.put((Class<Event>) e, new HashMap<>());
					}
					if (!listeners.get(e).containsKey(listener)) listeners.get(e).put(listener, new HashSet<>());
					listeners.get(e).get(listener).add(method);
				}
			}
		}
	}
	
	/**
	 * Fires the given event, propagating it through the {@link EventListener}s that accept it first.
	 * 
	 * @param e The {@link Event} to be fired.
	 * @return True if the event fired successfully (was not cancelled or stopped by an error).
	 */
	public final boolean fireEvent(Event e) {
		for (Entry<EventListener, Set<Method>> le : listeners.get(e.getClass()).entrySet()) {
			for (Method m : le.getValue()) {
				try {
					m.invoke(le.getKey(), e);
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					e1.printStackTrace();
				}
			}
		}
		return e.isCancelled() ? false : e.fire();
	}

}
