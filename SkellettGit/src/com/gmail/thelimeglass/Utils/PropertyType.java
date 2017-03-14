package com.gmail.thelimeglass.Utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ch.njol.skript.lang.ExpressionType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PropertyType {
    public ExpressionType value();
}

