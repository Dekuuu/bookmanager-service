package com.biyesheji.bookmanagerservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FavoriteDto {
    private int id;
    private String userName;            //收藏人
    private String bookName;            //书名
    private String categoryNo;          //类别编号
    private String author ;             //作者
    private int state;                  //收藏状态
    private int version;
    private Date createTime;
    private Date updateTime;
    private Integer startIndex;         //分页起始位置
    private Integer endIndex;           //分页结束位置
    private Integer pageSize;           //每页页数
    private Integer currentPage;        //当前页数
    private Integer total;              //总数
}
