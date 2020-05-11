package com.biyesheji.bookmanagerservice.aop;

import com.biyesheji.bookmanagerservice.utils.IpAddressUtil;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;

/**
* Description: 接口监控<br/>
* @author: wuwenguang<br/>
* @date: 2020/3/27 15:00<br/>
* @param:<br/>
* @return:
*/
@Aspect
@Component
@Log4j2
public class ControllerMonitorAspect {
    private DateFormat dateFormat = DateFormat.getDateTimeInstance();
    //定义切面，对应的controller下的方法且该方法有ControllerMonitorAnnotation注释标识，才能触发到该方法
    @Pointcut("execution(public * com.biyesheji.bookmanagerservice.controller.*.*(..))" )
    public void addAdvice(){};

    @Before("addAdvice()")
    public void before(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        String name = joinPoint.getSignature().getName();
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        log.info(IpAddressUtil.getIpAddress(request)+"于"+dateFormat.format(new Date())+"请求调用"+signature.getDeclaringTypeName()+"："+name+"方法");
    }

    @After("addAdvice()")
    public void after(){
    }
}
