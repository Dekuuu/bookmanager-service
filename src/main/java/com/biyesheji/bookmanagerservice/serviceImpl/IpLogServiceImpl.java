package com.biyesheji.bookmanagerservice.serviceImpl;

import com.biyesheji.bookmanagerservice.Enum.ApiResultEnum;
import com.biyesheji.bookmanagerservice.dto.IpLogDto;
import com.biyesheji.bookmanagerservice.dto.PageResult;
import com.biyesheji.bookmanagerservice.entity.IpLog;
import com.biyesheji.bookmanagerservice.entity.LoginInfo;
import com.biyesheji.bookmanagerservice.mapper.IpLogMapper;
import com.biyesheji.bookmanagerservice.service.IpLogService;
import com.biyesheji.bookmanagerservice.utils.HttpServletRequestUtil;
import com.biyesheji.bookmanagerservice.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IpLogServiceImpl implements IpLogService {
    @Autowired
    private IpLogMapper ipLogMapper;
    @Override
    public int insertIpLog(IpLog ipLog) {
        return ipLogMapper.insertIpLog(ipLog);
    }

    @Override
    public PageResult queryIpLoogs(IpLogDto ipLogDto) throws Exception{
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(), LoginInfo.class);
        if(loginInfo == null){
            throw new Exception("请先登录");
        }
        if(loginInfo.getUserType() == ApiResultEnum.USER.getIndex()){
            throw new Exception("你没有权限进行访问!");
        }
        List<IpLog> ipLogList = ipLogMapper.queryIpLog(ipLogDto);
        int ipLogCounts = ipLogMapper.queryIpLogCounts(ipLogDto);
        return new PageResult(ipLogList,ipLogDto.getStartIndex(),ipLogDto.getEndIndex(),ipLogDto.getPageSize(),ipLogDto.getCurrentPage(),ipLogCounts);
    }
}
