package com.biyesheji.bookmanagerservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
* Description: 账户信息实体类<br/>
* @author: wuwenguang<br/>
* @date: 2020/4/8 11:12<br/>
* @param:<br/>
* @return:
*/
@Data
public class LoginInfo implements Serializable {
    private Integer id;                 //主键id
    private String userName;            //用户名
    private String password;            //密码
    private Integer loginState;          //登录状态
    private Integer userType;            //用户类型编码
    private String userTypeName;            //用户类型中文
    private Date createTime;
    private Date updateTime;
    private Integer version;              //修改的版本号
    private String email;                 //邮件
}
