package com.biyesheji.bookmanagerservice.mapper;

import com.biyesheji.bookmanagerservice.dto.FavoriteDto;
import com.biyesheji.bookmanagerservice.entity.Book;

import javax.websocket.server.PathParam;
import java.util.List;

public interface FavoriteMapper {
    /**
    * Description: 根据用户查询个人收藏情况<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/29 10:17<br/>
    * @param:<br/>
    * @return:
    */
    public List<Book> queryByUserName(FavoriteDto favoriteDto);

    /**
    * Description: 查询个人收藏情况总数<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/29 16:06<br/>
    * @param:<br/>
    * @return:
    */
    public int queryCountsByUserName(FavoriteDto favoriteDto);

    /**
    * Description: 添加新的收藏记录<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/29 16:39<br/>
    * @param:<br/>
    * @return:
    */
    public int insertFavorite(FavoriteDto favoriteDto);

    /**
    * Description: 修改收藏记录<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/29 17:22<br/>
    * @param:<br/>
    * @return:
    */
    public int updateFavorite(FavoriteDto favoriteDto);
}
