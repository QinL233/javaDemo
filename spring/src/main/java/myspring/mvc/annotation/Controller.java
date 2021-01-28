package myspring.mvc.annotation;


import java.lang.annotation.*;

//controller注解，只能注解在类上。
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    //命名
    String value() default "";
}
