package com.biyesheji.bookmanagerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult {
    private List list;
    private Integer startIndex;         //分页起始位置
    private Integer endIndex;           //分页结束位置
    private Integer pageSize;           //每页页数
    private Integer currentPage;        //当前页数
    private Integer total;              //总数
}
