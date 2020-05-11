package com.biyesheji.bookmanagerservice.serviceImpl;

import com.biyesheji.bookmanagerservice.Enum.ApiResultEnum;
import com.biyesheji.bookmanagerservice.Enum.PreEnum;
import com.biyesheji.bookmanagerservice.dto.FavoriteDto;
import com.biyesheji.bookmanagerservice.dto.PageResult;
import com.biyesheji.bookmanagerservice.entity.Book;
import com.biyesheji.bookmanagerservice.entity.LoginInfo;
import com.biyesheji.bookmanagerservice.mapper.FavoriteMapper;
import com.biyesheji.bookmanagerservice.service.FavoriteService;
import com.biyesheji.bookmanagerservice.utils.HttpServletRequestUtil;
import com.biyesheji.bookmanagerservice.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteMapper favoriteMapper;
    @Override
    public PageResult queryByUserName(FavoriteDto favoriteDto) throws Exception{
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(), LoginInfo.class);
        if(loginInfo == null){
            throw new Exception("请先登录");
        }
        favoriteDto.setUserName(loginInfo.getUserName());
        List<Book> books = favoriteMapper.queryByUserName(favoriteDto);
        int count = favoriteMapper.queryCountsByUserName(favoriteDto);
        return new PageResult(books,favoriteDto.getStartIndex(),favoriteDto.getEndIndex(),favoriteDto.getPageSize(),favoriteDto.getCurrentPage(),count);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int favorite(FavoriteDto favoriteDto) throws Exception{
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(), LoginInfo.class);
        if(loginInfo == null){
            throw new Exception("请先登录");
        }
        favoriteDto.setUserName(loginInfo.getUserName());
//        先查询是否已经存在记录
        int counts = favoriteMapper.queryCountsByUserName(favoriteDto);
        if(counts != 0){
            throw new Exception("该记录已经存在!");
        }
        favoriteDto.setVersion(1);
        favoriteDto.setState(ApiResultEnum.FAVORITE.getIndex());
        try{
            return favoriteMapper.insertFavorite(favoriteDto);
        }catch (Exception e){
            throw new Exception("收藏失败");
        }
    }

    @Override
    public int cancel(FavoriteDto favoriteDto) throws Exception {
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(), LoginInfo.class);
        if(loginInfo == null){
            throw new Exception("请先登录");
        }
        favoriteDto.setUserName(loginInfo.getUserName());
        favoriteDto.setState(ApiResultEnum.NOT_FAVORITE.getIndex());
        try{
            return favoriteMapper.updateFavorite(favoriteDto);
        }catch (Exception e){
            throw new Exception("取消收藏失败");
        }
    }
}
