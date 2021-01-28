package myspring.mvc.annotation;


import java.lang.annotation.*;

/**
 * RequestMapping 可以在类和方法上
 *
 */

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    /**
     * 表示访问该方法的url
     *
     * @return
     */
    String value() default "";

}