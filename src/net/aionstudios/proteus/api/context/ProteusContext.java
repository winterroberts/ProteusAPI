package net.aionstudios.proteus.api.context;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.Inherited;

@Retention(RUNTIME)
@Target({TYPE, TYPE_USE})
@Inherited
public @interface ProteusContext {
	
	String[] path() default "/";
	boolean acceptChildren() default false;
	boolean preserveType() default false;

}
