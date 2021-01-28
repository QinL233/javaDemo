package myspring.mvc.annotation;

import java.lang.annotation.*;

/**
 * RequestParam注解,只能注解在参数上
 *
 */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {

    /**
     * 表示参数的别名，必填
     * @return
     */
    String value();
}
