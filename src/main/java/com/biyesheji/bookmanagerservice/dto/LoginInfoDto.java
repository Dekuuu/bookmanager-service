package com.biyesheji.bookmanagerservice.dto;

import com.biyesheji.bookmanagerservice.entity.LoginInfo;
import lombok.Data;

import java.util.Date;

/**
* Description: 登录信息--参数br/>
* @author: wuwenguang<br/>
* @date: 2020/3/14 15:35<br/>
* @param:<br/>
* @return:
*/
@Data
public class LoginInfoDto extends LoginInfo {
    private String code;                 //短信验证码
    private String originPsw ;           //旧的密码
    private String newPsw ;              //新的密码

    private Integer startIndex;         //分页起始位置
    private Integer endIndex;           //分页结束位置
    private Integer pageSize;           //每页页数
    private Integer currentPage;        //当前页数
    private Integer total;              //总数
}
