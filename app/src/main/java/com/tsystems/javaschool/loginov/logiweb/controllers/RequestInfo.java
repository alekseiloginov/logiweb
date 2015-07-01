package com.tsystems.javaschool.loginov.logiweb.controllers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Homebrew Spring RequestMapping annotation analog.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestInfo {
    String value();
    String method();
}
