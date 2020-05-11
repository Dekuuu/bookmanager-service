package com.biyesheji.bookmanagerservice.controller;

import com.biyesheji.bookmanagerservice.Enum.ApiResultEnum;
import com.biyesheji.bookmanagerservice.Enum.PreEnum;
import com.biyesheji.bookmanagerservice.dto.IpLogDto;
import com.biyesheji.bookmanagerservice.service.IpLogService;
import com.biyesheji.bookmanagerservice.utils.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book/iplog/")
@Api("登录日志控制器")
public class IplogController {
    @Autowired
    private IpLogService ipLogService;

    @ApiOperation("查询登录日志情况")
    @PostMapping("queryByPage")
    public ApiResult queryIpLogs(@RequestBody IpLogDto ipLogDto){
        try{
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),ipLogService.queryIpLoogs(ipLogDto));
        }catch (Exception e){
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(),e.getMessage());
        }
    }
}
