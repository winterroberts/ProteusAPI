package net.aionstudios.proteus.api.event;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
/**
 * An annotation for methods in {@link EventHandler}s to accept {@link Event}s of the methods argument type.
 * 
 * @author Winter Roberts
 *
 */
public @interface EventHandler {
	
}
