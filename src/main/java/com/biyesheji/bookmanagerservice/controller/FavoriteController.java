package com.biyesheji.bookmanagerservice.controller;

import com.biyesheji.bookmanagerservice.Enum.ApiResultEnum;
import com.biyesheji.bookmanagerservice.dto.FavoriteDto;
import com.biyesheji.bookmanagerservice.service.FavoriteService;
import com.biyesheji.bookmanagerservice.utils.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book/favorite/")
@Api(value="个人收藏")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("queryByPage")
    @ApiOperation("查询个人收藏信息")
    public ApiResult queryFavoriteByUserName(@RequestBody FavoriteDto favoriteDto){
        try{
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),favoriteService.queryByUserName(favoriteDto));
        }catch (Exception e){
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(),e.getMessage());
        }
    }

    @PostMapping("favorite")
    @ApiOperation("收藏图书")
    public ApiResult favorite(@RequestBody FavoriteDto favoriteDto){
        try{
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),favoriteService.favorite(favoriteDto));
        }catch (Exception e){
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(),e.getMessage());
        }
    }

    @PostMapping("updateFavorite")
    @ApiOperation("取消收藏")
    public ApiResult updateFavorite(@RequestBody FavoriteDto favoriteDto){
        try{
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),favoriteService.cancel(favoriteDto));
        }catch (Exception e){
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(),e.getMessage());
        }
    }
}
