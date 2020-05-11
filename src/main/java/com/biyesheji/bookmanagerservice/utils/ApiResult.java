package com.biyesheji.bookmanagerservice.utils;

import lombok.Data;

import java.util.List;

/**
* Description: 接口返回状态信息<br/>
* @author: wuwenguang<br/>
* @date: 2020/3/15 10:35<br/>
* @param:<br/>
* @return:
*/
@Data
public class ApiResult {
    private int code;            //返回的状态码
    private Object data;      //返回的数据

    public ApiResult(){
        super();
    };

    public ApiResult(int code,Object data){
        this.code= code;
        this.data=data;
    };

    public static ApiResult success(int code,Object object){
        return new ApiResult(code,object);
    }
}
