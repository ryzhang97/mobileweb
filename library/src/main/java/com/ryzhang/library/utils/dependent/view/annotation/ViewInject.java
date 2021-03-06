package com.ryzhang.library.utils.dependent.view.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ryzhang
 * @date 2017/10/23
 * @time 14:29
 * Project
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewInject {
    int value();

    int parentId() default 0;
}