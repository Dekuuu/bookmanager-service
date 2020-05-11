package com.biyesheji.bookmanagerservice.controller;

import com.biyesheji.bookmanagerservice.Enum.ApiResultEnum;
import com.biyesheji.bookmanagerservice.dto.BookDto;
import com.biyesheji.bookmanagerservice.dto.BorrowDto;
import com.biyesheji.bookmanagerservice.dto.LoginInfoDto;
import com.biyesheji.bookmanagerservice.entity.LoginInfo;
import com.biyesheji.bookmanagerservice.service.AdminService;
import com.biyesheji.bookmanagerservice.utils.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("管理员控制器")
@RequestMapping("/book/admin/")
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation("分页查询所有的用户信息")
    @PostMapping("queryByPage")
    public ApiResult queryByPage(@RequestBody LoginInfoDto loginInfoDto) {
        return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(), adminService.queryAllLoginInfo(loginInfoDto));
    }

    @ApiOperation("修改用户信息")
    @PostMapping("updateUser")
    public ApiResult updateUser(@RequestBody LoginInfoDto loginInfo) {
        try{
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(), adminService.updateUser(loginInfo));
        }catch (Exception e){
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(), e.getMessage());
        }
    }

    @ApiOperation("新增用户信息")
    @PostMapping("addUser")
    public ApiResult addUser(@RequestBody LoginInfoDto loginInfo) {
        try{
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(), adminService.insertUser(loginInfo));
        }catch (Exception e){
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(), e.getMessage());
        }
    }

    @ApiOperation("分页查询所有的书本信息")
    @PostMapping("queryBooksByPage")
    public ApiResult queryBooksByPage(@RequestBody BookDto bookDto) {
        return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(), adminService.queryBooksByPage(bookDto));
    }

    @ApiOperation("新增图书信息")
    @PostMapping("addBook")
    public ApiResult addBook(@RequestBody BookDto bookDto) {
        try {
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(), adminService.insertBook(bookDto));
        } catch (Exception e) {
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(), e.getMessage());
        }
    }

    @ApiOperation("修改图书信息")
    @PostMapping("updateBook")
    public ApiResult updateBook(@RequestBody BookDto bookDto) {
        try{
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(), adminService.updateBook(bookDto));
        }catch (Exception e) {
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(), e.getMessage());
        }

    }

    @ApiOperation("根据图书编号查询该图书借阅信息")
    @PostMapping("queryByBookNo")
    public ApiResult queryByBookNo(@RequestBody BorrowDto borrowDto) {
        return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(), adminService.queryByBookNo(borrowDto));
    }

    @ApiOperation("归还图书")
    @PostMapping("returnBook")
    public ApiResult returnBook(@RequestBody BorrowDto borrowDto) {
        try {
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(), adminService.returnBook(borrowDto));
        } catch (Exception e) {
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(), e.getMessage());
        }
    }

    @ApiOperation("借阅图书")
    @PostMapping("borrowBook")
    public ApiResult borrowBook(@RequestBody BorrowDto borrowDto) {
        try {
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(), adminService.borrowBook(borrowDto));
        } catch (Exception e) {
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(), e.getMessage());
        }
    }

    @ApiOperation("续借图书")
    @PostMapping("reNewBook")
    public ApiResult reNewBook(@RequestBody BorrowDto borrowDto) {
        try {
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(), adminService.reNewBook(borrowDto));
        } catch (Exception e) {
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(), e.getMessage());
        }
    }

    @ApiOperation("查询待审核书籍数量")
    @GetMapping("queryNotCheck")
    public ApiResult queryNotCheck() {
        return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(), adminService.queryNotCheck());
    }

    @ApiOperation("审核通过图书")
    @PostMapping("passCheck")
    public ApiResult passCheck(@RequestBody BookDto bookDto) {
        try {
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(), adminService.passCheck(bookDto));
        } catch (Exception e) {
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(), e.getMessage());
        }
    }

    @ApiOperation("审核不通过图书")
    @PostMapping("notPassCheck")
    public ApiResult notPassCheck(@RequestBody BookDto bookDto) {
        try {
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(), adminService.notPassCheck(bookDto));
        } catch (Exception e) {
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(), e.getMessage());
        }
    }
}
