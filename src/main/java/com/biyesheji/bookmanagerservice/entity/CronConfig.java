package com.biyesheji.bookmanagerservice.entity;

import lombok.Data;

/**
* Description: 定时任务配置实体类<br/>
* @author: wuwenguang<br/>
* @date: 2020/4/8 11:02<br/>
* @param:<br/>
* @return:
*/
@Data
public class CronConfig {
    private int id;
    private String keyValue;        //定时任务关键字
    private String switchOn;        //定时任务开关
    private String excuteTime;      //执行时间
    private String description;     //定时任务描述
}
