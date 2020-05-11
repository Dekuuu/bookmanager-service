package com.biyesheji.bookmanagerservice.mapper;

import com.biyesheji.bookmanagerservice.dto.IpLogDto;
import com.biyesheji.bookmanagerservice.entity.IpLog;

import java.util.List;

public interface IpLogMapper {
    /**
    * Description: 添加登录日志<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/27 16:11<br/>
    * @param:<br/>
    * @return:
    */
    public int insertIpLog(IpLog ipLog);

    /**
    * Description: 登录日志查询<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/3 9:34<br/>
    * @param:<br/>
    * @return:
    */
    public List<IpLog> queryIpLog(IpLogDto ipLogDto);

    /**
     * Description:  统计登录日志记录条数<br/>
     * @author: wuwenguang<br/>
     * @date: 2020/4/3 9:54<br/>
     * @param:<br/>
     * @return:
     */
    public int queryIpLogCounts(IpLogDto ipLogDto);
}
