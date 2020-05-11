package com.biyesheji.bookmanagerservice.mapper;

import com.biyesheji.bookmanagerservice.entity.BookDict;
import org.apache.ibatis.annotations.MapKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BookDictMapper {
    /**
    * Description: 查询书本信息字典表<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/18 21:15<br/>
    * @param:<br/>
    * @return:
    */
    public List<Map<String,String>> queryBookDict();

    /**
     * Description: 查询书本信息字典表<br/>
     * @author: wuwenguang<br/>
     * @date: 2020/3/18 21:15<br/>
     * @param:<br/>
     * @return:
     */
    public List<BookDict> queryBookDictEntity();
}
