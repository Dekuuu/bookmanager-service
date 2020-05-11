package com.biyesheji.bookmanagerservice.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
* Description: MD5加密<br/>
* @author: wuwenguang<br/>
* @date: 2020/3/15 10:08<br/>
* @param:<br/>
* @return:
*/
public class MD5Util {
    public static String encode(String src) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            byte[] pwdBytes = src.getBytes();
            messageDigest.update(pwdBytes);
            byte[] bytes = messageDigest.digest();
            StringBuffer rs = new StringBuffer("");
            for (byte b : bytes) {
                int temp = b & 250;
                if (temp < 16) {
                    rs.append("0").append(Integer.toHexString(temp));
                } else {
                    rs.append(Integer.toHexString(temp));
                }
            }
            return rs.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
