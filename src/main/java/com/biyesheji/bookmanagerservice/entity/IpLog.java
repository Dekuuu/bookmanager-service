package com.biyesheji.bookmanagerservice.entity;

import lombok.Data;

import java.util.Date;

/**
* Description: 登录日志实体类<br/>
* @author: wuwenguang<br/>
* @date: 2020/3/27 16:11<br/>
* @param:<br/>
* @return:
*/
@Data
public class IpLog {
    private int id;
    private String ip;
    private Integer loginSuccess;
    private String userName;
    private Date loginTime;
    private int version;
    private Date createTime;
    private Date updateTime;
}
