package de.dpunkt.myaktion.util;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

public class LogTypes {
	@Qualifier
	@Target({METHOD, FIELD, PARAMETER, TYPE})
	@Retention(RUNTIME)
	public @interface FachLog {
	}

	@Qualifier
	@Target({METHOD, FIELD, PARAMETER, TYPE})
	@Retention(RUNTIME)
	public @interface TecLog {
	}
}
