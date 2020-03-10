package com.zzq.mybatis.annotation;

import com.zzq.mybatis.AutoConfigurationImport;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * mapper类扫描包
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AutoConfigurationImport.class)
public @interface MapperScanner {

    String value() default "";

}
