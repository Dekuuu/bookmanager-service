package com.biyesheji.bookmanagerservice.dto;

import lombok.Data;

import java.util.Date;

/**
* Description: 书籍查询条件<br/>
* @author: wuwenguang<br/>
* @date: 2020/3/13 20:38<br/>
* @param:<br/>
* @return:
*/
@Data
public class BookDto {
    private Integer id;                 //主键id
    private String bookNo;              //书本编号
    private String bookName;            //书名
    private String categoryNo;          //类目编号
    private String imageUrl;            //图片地址
    private String description;         //书本描述
    private String author;              //作者
    private Date createTime;
    private Date updateTime;
    private Integer version;              //修改的版本号
    private Integer state;               //书本借出状态
    private Integer startIndex;         //分页起始位置
    private Integer endIndex;           //分页结束位置
    private Integer pageSize;           //每页页数
    private Integer currentPage;        //当前页数
    private Integer total;              //总数
    private String donaterName;             //捐赠人
    private Date donateTime ;               //捐赠时间
}
