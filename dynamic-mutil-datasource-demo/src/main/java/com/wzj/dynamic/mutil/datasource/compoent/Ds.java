package com.wzj.dynamic.mutil.datasource.compoent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wzj on 2023/3/26 20:33
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Ds {
    String value() default DynamicDataSourceConstants.DS1_ROUTEING_KEY;
}
