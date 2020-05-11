package com.biyesheji.bookmanagerservice.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Book {
    private int id;
    private String bookNo;
    private String bookName;
    private String categoryNo;
    private String categoryNoName;              //类别编号中文
    private String imageUrl;
    private String description;
    private String author;
    private Date createTime;
    private Date updateTime;
    private int version;
    private int state;
    private String stateName;                   //状态中文
    private int counts;                         //统计数量
    private String donater ;                    //捐赠者
    private Date donateTime;                    //捐赠时间
}
