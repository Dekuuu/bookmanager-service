package com.biyesheji.bookmanagerservice.entity;

import lombok.Data;
/**
* Description: 当月热门借阅信息实体类<br/>
* @author: wuwenguang<br/>
* @date: 2020/3/24 10:51<br/>
* @param:<br/>
* @return:
*/
@Data
public class BorrowingResult {
    private String borrowingBookNoName;
    private int counts;
    private String imageUrl ;
}
