package com.biyesheji.bookmanagerservice.Enum;


/**
* Description: 状态返回结果枚举类<br/>
* @author: wuwenguang<br/>
* @date: 2020/3/15 10:51<br/>
* @param:<br/>
* @return:
*/
public enum ApiResultEnum {
    SUCCESS("成功", 1),
    FAILED("失败", 0),            //接口调用状态码
    LOGINED("已登录",1),
    NOT_LOGIN("未登录",0),         //登录状态
    USER("用户",1),
    MANAGER("管理员",0),           //角色
    CHECK_FAILED("审核失败",3),
    NOT_CHECK("待审核",2),
    LENT("已借出",1),
    NOT_LEND("未借出",0),          //书本状态
    RETURNED("已归还",1),
    NOT_RETURN("未归还",0),        //借阅状态
    FAVORITE("收藏",1),
    NOT_FAVORITE("取消收藏",0);     //收藏状态
    // 成员变量
    private String name;
    private int index;
    // 构造方法
    private ApiResultEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
    // 普通方法
    public static String getName(int index) {
        for (ApiResultEnum c : ApiResultEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    // get set 方法
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}
