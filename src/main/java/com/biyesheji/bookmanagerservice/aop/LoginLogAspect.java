package com.biyesheji.bookmanagerservice.aop;

import com.biyesheji.bookmanagerservice.Enum.ApiResultEnum;
import com.biyesheji.bookmanagerservice.dto.LoginInfoDto;
import com.biyesheji.bookmanagerservice.entity.IpLog;
import com.biyesheji.bookmanagerservice.service.IpLogService;
import com.biyesheji.bookmanagerservice.utils.ApiResult;
import com.biyesheji.bookmanagerservice.utils.IpAddressUtil;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

/**
* Description: 登录日志<br/>
* @author: wuwenguang<br/>
* @date: 2020/3/27 15:00<br/>
* @param:<br/>
* @return:
*/
@Aspect
@Component
@Log4j2
public class LoginLogAspect {
    @Autowired
    private IpLogService ipLogMapper;

    //定义切面，对应的controller下的方法且该方法有LoginCheckAnnotation注释标识，才能触发到该方法
    @Pointcut("execution(public * com.biyesheji.bookmanagerservice.controller.LoginInfoController.login(..)) && @annotation(com.biyesheji.bookmanagerservice.annotation.LoginLogAnnotation)" )
    public void addAdvice(){};

    @Before("addAdvice()")
    public void before(JoinPoint joinPoint){
//        可以用手动抛出异常来中断方法的执行
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length==0){
            log.info("》》》》》》》》》》》登录参数为空!!!");
            return ;
        }
        LoginInfoDto loginInfoDto =(LoginInfoDto) args[0];
        log.info("》》》》》》》》》"+new Date()+"--"+loginInfoDto.getUserName()+"尝试登录");
    }

    @AfterReturning(value="addAdvice()",returning = "result")
    public void after(JoinPoint joinPoint,Object result){
        ApiResult apiResult = (ApiResult) result;
        Object[] args = joinPoint.getArgs();
        LoginInfoDto loginInfoDto =(LoginInfoDto) args[0];
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        log.info("》》》》》》》》》》》》》》登录日志入库");
        IpLog ipLog = new IpLog();
        ipLog.setIp(IpAddressUtil.getIpAddress(request));
        ipLog.setLoginSuccess(apiResult.getCode());
        ipLog.setVersion(1);
        ipLog.setUserName(loginInfoDto.getUserName());
        ipLogMapper.insertIpLog(ipLog);
    }
}
