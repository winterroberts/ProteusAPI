package net.winrob.proteus.api.context;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.Inherited;

@Retention(RUNTIME)
@Target({TYPE, TYPE_USE})
@Inherited
/**
 * An optional annotation-style for registering a {@link ProteusHttpContext} or {@link ProteusWebSocketContext}
 * to an {@link EndpointConfiguration} in a {@link Router}.
 * 
 * @author Winter Roberts
 *
 */
public @interface ProteusContext {
	
	/**
	 * @return The path string(s) for this context, which will be converted to a {@link PathInterpreter}.
	 */
	String[] path();
	/**
	 * @return Whether or not the annotated {@link ProteusHttpContext}/{@link ProteusWebSocketContext} should retain
	 * existing path information or be overriden by the annotation.
	 */
	boolean preserveType() default false;

}
