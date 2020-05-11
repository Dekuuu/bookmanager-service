package com.biyesheji.bookmanagerservice.utils;

/**
* Description: 生成随机的验证码<br/>
* @author: wuwenguang<br/>
* @date: 2020/3/15 10:10<br/>
* @param:<br/>
* @return:
*/
public class CodeUtil {
    public static String CreateRandowCode() {
        int min=0;
        int max=9;
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<6;i++) {
            int random=(int) (Math.random()*(max-min)+min);
            sb.append(random+"");
        }
        return sb.toString();
    }
}
