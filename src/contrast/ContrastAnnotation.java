package contrast;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description:
 * @Date: Created in 16:37 2018/8/21
 * @Auth: LinJiangMing
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ContrastAnnotation {

    String frontMsg() default "";

    String middleMsg() default "change to";

    String backMsg() default "";

    String name() default "";

    String nullMsg() default "null";

}
