package cn.clboy.demo.aop.spring.annos;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author clboy
 */

@Target(METHOD)
@Retention(RUNTIME)
public @interface RequireToken {

    String value() default "";

    String tokenName() default "token";
}
