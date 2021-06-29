package net.aionstudios.proteus.api.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

public class EventManager {
	
	private static EventManager self;
	private static HashMap<Class<Event>, HashMap<EventListener, Set<Method>>> listeners;
	
	private EventManager() {
		self = this;
	}
	
	public static EventManager getInstance() {
		return self != null ? self : new EventManager();
	}
	
	@SuppressWarnings("unchecked")
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
