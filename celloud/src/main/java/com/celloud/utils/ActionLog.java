package com.celloud.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionLog {
    @AliasFor("operate") // 别名
    public String value();

    public String button() default "";

    public String[] buttons() default {};

    @AliasFor("value")
    public String operate() default "";
}
