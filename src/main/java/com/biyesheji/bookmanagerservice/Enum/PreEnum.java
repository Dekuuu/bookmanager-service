package com.biyesheji.bookmanagerservice.Enum;

/**
* Description: 前缀枚举<br/>
* @author: wuwenguang<br/>
* @date: 2020/3/16 15:13<br/>
* @param:<br/>
* @return:
*/
public enum PreEnum {

    PHONECODE("phoneCode", "phoneCode-"),           //验证码
    LOGINED_PRE("book_login","book_login-"),        //登录
    CHECK_EMAIL_OVERDUE("overdue","overdue"),       //查询逾期借阅记录
    EMAIL_MENTION("emailMention","emailMention"),   //邮件提醒
    EMAIL_BIND("email","email-"),                   //邮件绑定
    POPULAR_BOOKS("popularBooks","popularBooks");
    // 成员变量
    private String key;
    private String value;
    // 构造方法
    private PreEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }
    // 普通方法
    public static String getValue(String value) {
        for (PreEnum c : PreEnum.values()) {
            if (c.getValue() == value) {
                return c.value;
            }
        }
        return null;
    }
    // get set 方法
    public String getKey() {
        return key;
    }
    public void setKey(String name) {
        this.key = name;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
