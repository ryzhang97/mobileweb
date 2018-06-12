package com.ryzhang.library.utils.dependent.view.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ryzhang
 * @date 2017/11/6
 * @time 18:10
 * Project
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Event {
    int[] value();

    int[] parentId() default {0};

    Class<?> type() default View.OnClickListener.class;

    String setter() default "";

    String method() default "";
}

