package com.biyesheji.bookmanagerservice.controller;

import com.biyesheji.bookmanagerservice.Enum.ApiResultEnum;
import com.biyesheji.bookmanagerservice.Enum.PreEnum;
import com.biyesheji.bookmanagerservice.annotation.ControllerMonitorAnnotation;
import com.biyesheji.bookmanagerservice.dto.BookDto;
import com.biyesheji.bookmanagerservice.dto.BorrowDto;
import com.biyesheji.bookmanagerservice.entity.BorrowingInfo;
import com.biyesheji.bookmanagerservice.service.BorrowingService;
import com.biyesheji.bookmanagerservice.utils.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
@Api(value = "借阅信息控制器",tags = {"ookManager-service"})
@RequestMapping("/book/borrowinfo")
public class BorrowingController {
    @Autowired
    private BorrowingService borrowingServiceImpl;

    @PostMapping("/queryByPage")
    @ApiOperation("分页查询借阅信息")
    public ApiResult queryBooks(@RequestBody BorrowDto borrowDto){
        try{
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),borrowingServiceImpl.queryBorrowingInfo(borrowDto));
        }catch (Exception e){
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),e.getMessage());
        }

    }

    @PostMapping("/updateBorrowInfo")
    @ApiOperation("续借")
    public ApiResult renew(@RequestBody BorrowDto borrowDto){
        try{
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),borrowingServiceImpl.renew(borrowDto));
        }catch (Exception e){
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),e.getMessage());
        }

    }

    @GetMapping("/queryBorrowingByMonth")
    @ApiOperation("当月热门书籍统计")
    public ApiResult queryAllBrowingInfoByMonth(){
        return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),borrowingServiceImpl.queryAllBrowingInfoByMonth());
    }
}
