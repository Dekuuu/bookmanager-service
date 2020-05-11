package com.biyesheji.bookmanagerservice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
* Description: 自定义登录注解<br/>
* @author: wuwenguang<br/>
* @date: 2020/3/27 15:00<br/>
* @param:<br/>
* @return:
*/
@Retention(RetentionPolicy.RUNTIME)         //定义注解的保留策略，注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target(ElementType.TYPE)        //定义注解的作用目标
public @interface ControllerMonitorAnnotation {
    String name();
}
