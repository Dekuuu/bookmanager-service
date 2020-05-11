package com.biyesheji.bookmanagerservice.mapper;

import com.biyesheji.bookmanagerservice.dto.BookDto;
import com.biyesheji.bookmanagerservice.entity.Book;

import java.util.List;
import java.util.Map;

public interface BookInfoMapper {

    /**
    * Description: 分页查询书本信息<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/13 20:43<br/>
    * @param:<br/>
    * @return:
    */
    public List<Book> queryBooksByPage(BookDto bto);

    /**
    * Description: 添加书籍信息<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/14 15:13<br/>
    * @param:<br/>
    * @return:
    */
    public int insertBook(BookDto bookDto);

    /**
    * Description: 修改图书信息<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/14 15:25<br/>
    * @param:<br/>
    * @return:
    */
    public int updateBook(BookDto bookDto);

    /**
    * Description: 统计书本总数量<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/18 15:59<br/>
    * @param:<br/>
    * @return:
    */
    public int queryBooksByPageCount(BookDto bookDto);

    /**
    * Description: 查询单本书籍<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/19 21:04<br/>
    * @param:<br/>
    * @return:
    */
    public List<Book> queryBookSingleByCondition(BookDto bookDto);

    /**
    * Description: 分页查询书籍借阅（按照书名统计）情况<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/21 10:16<br/>
    * @param:<br/>
    * @return:
    */
    public List<Book> queryBooksGroup(BookDto bookDto);

    /**
     * Description: 可借阅书籍统计数量<br/>
     * @author: wuwenguang<br/>
     * @date: 2020/3/21 14:45<br/>
     * @param:<br/>
     * @return:
     */
    public List<Map<String,Integer>> queryBooksGroupCount();

    /**
    * Description: 检查待处理的书籍统计信息<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/11 23:14<br/>
    * @param:<br/>
    * @return:
    */
    public int queryNotCheckCount();

    /**
    * Description: 查询所有书籍的数量（分组）<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/16 21:39<br/>
    * @param:<br/>
    * @return:
    */
    public int queryBooksGroupCounts(BookDto bookDto);
}
