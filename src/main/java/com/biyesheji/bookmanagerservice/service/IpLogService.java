package com.biyesheji.bookmanagerservice.service;

import com.biyesheji.bookmanagerservice.dto.IpLogDto;
import com.biyesheji.bookmanagerservice.dto.PageResult;
import com.biyesheji.bookmanagerservice.entity.IpLog;

public interface IpLogService {
    /**
    * Description: 添加登录日志<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/3 9:49<br/>
    * @param:<br/>
    * @return:
    */
    public int insertIpLog(IpLog ipLog);

    /**
    * Description: 分页查询登录日志<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/3 9:49<br/>
    * @param:<br/>
    * @return:
    */
    public PageResult queryIpLoogs(IpLogDto ipLogDto) throws Exception;

}
