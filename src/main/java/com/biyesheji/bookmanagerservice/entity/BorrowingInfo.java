package com.biyesheji.bookmanagerservice.entity;

import lombok.Data;

import java.util.Date;

/**
* Description: 借阅信息实体类<br/>
* @author: wuwenguang<br/>
* @date: 2020/3/21 17:01<br/>
* @param:<br/>
* @return:
*/
@Data
public class BorrowingInfo {
    private Integer id;
    private String borrowingName;       //借阅人
    private String borrowingBookNo;     //借阅书籍的编号
    private String borrowingBookNoName;     //借阅书籍名字
    private String imageUrl;                //书籍图片
    private Integer renewAble;                  //可续借次数
    private Integer returnState;                //归还状态
    private Date borrowingTime;                  //借阅时间
    private Date shouldReturnTime;               //应归还时间
    private Date returnTime;                     //归还时间
    private Integer version;
    private Date createTime;
    private Date updateTime;

}
