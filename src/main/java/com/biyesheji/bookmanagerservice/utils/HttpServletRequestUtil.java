package com.biyesheji.bookmanagerservice.utils;

import com.biyesheji.bookmanagerservice.Enum.PreEnum;
import com.biyesheji.bookmanagerservice.entity.LoginInfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
* Description: 业务层中获取request对象<br/>
* @author: wuwenguang<br/>
* @date: 2020/3/15 10:12<br/>
* @param:<br/>
* @return:
*/
public class HttpServletRequestUtil {
    /*
     * 用于获取HttpSession
     */
    public static HttpSession getSession() {
        return ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).
                getRequest().getSession();
    }

    /*
     * 设置登录信息到session
     */
//    public static void setLogininfo(LoginInfo logininfo) {
//        getSession().setAttribute(LOGININFO_TOKEN, logininfo);
//    }

    /*
     * 获取登录信息
     */
//    public static LoginInfo getLogininfo() {
//        return (LoginInfo) getSession().getAttribute(LOGININFO_TOKEN);
//    }

    /*
     * 获取Http请求
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).
                getRequest();
    }

    /**
    * Description: 获取登录的cookie token信息<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/28 22:55<br/>
    * @param:<br/>
    * @return:
    */
    public static String getCookieToken(){
        HttpServletRequest httpServletRequest = HttpServletRequestUtil.getHttpServletRequest();
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies == null || cookies.length == 0){
            return null;
        }
        Cookie cookie = null;
        for(Cookie c : cookies){
            if(c.getName().equals(PreEnum.LOGINED_PRE.getValue())){
                cookie = c;
                break ;
            }
        }
        if(cookie == null ){
            return null ;
        }
        return cookie.getValue();
    }

    /**
    * Description: 返回response<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/21 22:56<br/>
    * @param:<br/>
    * @return:
    */
    public static HttpServletResponse getHttpServletResponse() {
        return ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).
                getResponse();
    }
    /**
     *
     * 设置cookie
     * @param name
     * @param value
     */
    public static void setCookie(String name,String value) {
        HttpServletResponse response = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).
                getResponse();
        Cookie cookie=new Cookie(name,value);
        response.addCookie(cookie);
    }

    /**
     * 获取cookie
     *
     */
    public static Cookie getCookie(String name) {
        Cookie[] cookies = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).
                getRequest().getCookies();
        for(Cookie c:cookies) {
            if(c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }
}
