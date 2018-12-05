package com.fanjia.mice.util.contrast.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 递归属性比较注解
 * @Date: Created in 9:14 2018/8/22
 * @Auth: LinJiangMing
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ContrastRecursive {

    String name() default "记录";

    String addMsg() default "新增";

    String delMsg() default "删除";

    String changeMsg() default "修改";
}

