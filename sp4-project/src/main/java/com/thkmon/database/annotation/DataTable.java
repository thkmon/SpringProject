package com.thkmon.database.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.persistence.ColumnResult;

import org.hibernate.annotations.Table;

@Target({TYPE})
@Retention(RUNTIME)
public @interface DataTable {
	String name() default "";
}
