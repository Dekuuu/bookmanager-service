package com.biyesheji.bookmanagerservice.dto;

import com.biyesheji.bookmanagerservice.entity.BorrowingInfo;
import lombok.Data;

import java.util.Date;

@Data
public class BorrowDto extends BorrowingInfo {
    private Integer startIndex;         //分页起始位置
    private Integer endIndex;           //分页结束位置
    private Integer pageSize;           //每页页数
    private Integer currentPage;        //当前页数
    private Integer total;              //总数

    private Integer returnStateNew ;
}
