package com.biyesheji.bookmanagerservice.dto;

import com.biyesheji.bookmanagerservice.entity.IpLog;
import lombok.Data;

/**
* Description: 登录日志查询条件<br/>
* @author: wuwenguang<br/>
* @date: 2020/4/3 9:37<br/>
* @param:<br/>
* @return:
*/
@Data
public class IpLogDto extends IpLog {
    private Integer startIndex;         //分页起始位置
    private Integer endIndex;           //分页结束位置
    private Integer pageSize;           //每页页数
    private Integer currentPage;        //当前页数
    private Integer total;              //总数

    private String loginTimeSearch ;
}
