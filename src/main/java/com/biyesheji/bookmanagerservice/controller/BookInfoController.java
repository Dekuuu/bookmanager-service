package com.biyesheji.bookmanagerservice.controller;

import com.biyesheji.bookmanagerservice.Enum.ApiResultEnum;
import com.biyesheji.bookmanagerservice.annotation.ControllerMonitorAnnotation;
import com.biyesheji.bookmanagerservice.dto.BookDto;
import com.biyesheji.bookmanagerservice.service.BookDictService;
import com.biyesheji.bookmanagerservice.service.BookService;
import com.biyesheji.bookmanagerservice.utils.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(value="图书信息控制器",tags = {"bookManager-service"})
@RequestMapping("/book/bookInfo")
@CrossOrigin(value = "*")
public class BookInfoController {
    @Autowired
    private BookService bookServiceImpl;
    @Autowired
    private BookDictService bookDictService;

    @PostMapping("/queryByPage")
    @ApiOperation("分页查询书本信息")
    public ApiResult queryBooks(@RequestBody  BookDto bookDto){
        return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),bookServiceImpl.queryBooks(bookDto));
    }

    @GetMapping("/getAllDicts")
    @ApiOperation("获取字典表信息")
    public ApiResult queryBookDicts(){
        return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),bookDictService.queryAllBookDicts());
    }

    @PostMapping("/updateBook")
    @ApiOperation("修改书籍信息")
    public ApiResult updateBook(@RequestBody BookDto bookDto,@RequestParam(value = "file", required=false) MultipartFile multipartFile){
        try{
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),bookServiceImpl.updateBook(bookDto));
        }catch(Exception e){
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(),e.getMessage());
        }

    }


    @PostMapping("/updateImage")
    @ApiOperation("修改书籍图片")
    public ApiResult updateImage(@RequestParam(value = "file", required=false) MultipartFile multipartFile){
        try{
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),bookServiceImpl.updateImage(multipartFile));
        }catch(Exception e){
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(),e.getMessage());
        }
    }

    @PostMapping("/addImage")
    @ApiOperation("新增书籍图片")
    public ApiResult addImage(@RequestParam(value = "file", required=false) MultipartFile multipartFile){
        try{
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),bookServiceImpl.addImage(multipartFile));
        }catch(Exception e){
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(),e.getMessage());
        }
    }

    @PostMapping("/addBook")
    @ApiOperation("添加书籍信息")
    public ApiResult addBook(@RequestBody BookDto bookDto){
        try{
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),bookServiceImpl.insertBook(bookDto));
        }catch (Exception e){
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(),e.getMessage());
        }
    }

    @PostMapping("/reCheck")
    @ApiOperation("重新审核")
    public ApiResult reCheck(@RequestBody BookDto bookDto){
        try{
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),bookServiceImpl.reCheck(bookDto));
        }catch (Exception e){
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(),e.getMessage());
        }
    }

    @PostMapping("/queryByGroup")
    @ApiOperation("分页查询书本统计信息")
    public ApiResult queryBooksByGroup(@RequestBody  BookDto bookDto){
        return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),bookServiceImpl.queryBooksByGroup(bookDto));
    }
}
