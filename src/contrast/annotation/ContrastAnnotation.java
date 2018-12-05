package com.fanjia.mice.util.contrast.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 比较注解
 * @Date: Created in 16:37 2018/8/21
 * @Auth: LinJiangMing
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ContrastAnnotation {

    //前端字段
    String frontMsg() default "从";

    //中间字段
    String middleMsg() default "修改为";

    //后端字段
    String backMsg() default "";

    //属性名称
    String name() default "";

    //空值名称
    String nullMsg() default "";

    //新增字段
    String addMsg() default "新增";

    //删除字段
    String delMsg() default "删除";

}
