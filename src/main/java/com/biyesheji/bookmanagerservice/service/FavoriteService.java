package com.biyesheji.bookmanagerservice.service;

import com.biyesheji.bookmanagerservice.dto.FavoriteDto;
import com.biyesheji.bookmanagerservice.dto.PageResult;
import com.biyesheji.bookmanagerservice.entity.Book;

import java.util.List;

public interface FavoriteService {
    /**
    * Description:  根据用户名查询收藏的书籍<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/29 15:57<br/>
    * @param:<br/>
    * @return:
    */
    public PageResult queryByUserName(FavoriteDto favoriteDto) throws Exception;

    /**
    * Description: 收藏图书<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/29 17:21<br/>
    * @param:<br/>
    * @return:
    */
    public int favorite(FavoriteDto favoriteDto) throws Exception;

    /**
    * Description: 取消收藏<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/29 17:21<br/>
    * @param:<br/>
    * @return:
    */
    public int cancel(FavoriteDto favoriteDto) throws  Exception;
}
