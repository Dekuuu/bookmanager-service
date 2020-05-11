package com.biyesheji.bookmanagerservice.service;

import com.biyesheji.bookmanagerservice.dto.BookDto;
import com.biyesheji.bookmanagerservice.dto.PageResult;
import com.biyesheji.bookmanagerservice.entity.Book;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BookService {

    /**
    * Description: 分页查询所有的信息<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/15 10:04<br/>
    * @param:<br/>
    * @return:
    */
    public PageResult queryBooks(BookDto bookDto);

    /**
    * Description: 修改图书信息<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/19 14:23<br/>
    * @param:<br/>
    * @return:
    */
    public int updateBook(BookDto bookDto) throws Exception;

    /**
    * Description: 添加书籍信息<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/20 20:40<br/>
    * @param:<br/>
    * @return:
    */
    public int insertBook(BookDto bookDto) throws Exception;

    /**
    * Description: 分页查询统计书本借阅信息<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/21 10:26<br/>
    * @param:<br/>
    * @return:
    */
    public PageResult queryBooksByGroup(BookDto bookDto);

    /**
    * Description: 修改图片<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/26 20:10<br/>
    * @param:<br/>
    * @return:
    */
    public String updateImage(MultipartFile file) throws Exception;

    /**
    * Description: 新增图书图片<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/26 21:25<br/>
    * @param:<br/>
    * @return:
    */
    public String addImage(MultipartFile file) throws Exception;

    /**
    * Description: 书本重新审核<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/12 15:37<br/>
    * @param:<br/>
    * @return:
    */
    public int reCheck(BookDto bookDto) throws Exception;
}
