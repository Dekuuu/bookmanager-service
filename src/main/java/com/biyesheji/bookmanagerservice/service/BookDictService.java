package com.biyesheji.bookmanagerservice.service;

import com.biyesheji.bookmanagerservice.entity.BookDict;

import java.util.List;

public interface BookDictService {
    /**
    * Description: 查询所有的图书字典信息<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/18 22:49<br/>
    * @param:<br/>
    * @return:
    */
    public List<BookDict> queryAllBookDicts();
}
