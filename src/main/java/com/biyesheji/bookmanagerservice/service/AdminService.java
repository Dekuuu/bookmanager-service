package com.biyesheji.bookmanagerservice.service;

import com.biyesheji.bookmanagerservice.dto.BookDto;
import com.biyesheji.bookmanagerservice.dto.BorrowDto;
import com.biyesheji.bookmanagerservice.dto.LoginInfoDto;
import com.biyesheji.bookmanagerservice.dto.PageResult;
import com.biyesheji.bookmanagerservice.entity.Book;
import com.biyesheji.bookmanagerservice.entity.BorrowingInfo;
import com.biyesheji.bookmanagerservice.entity.LoginInfo;

import java.util.List;

/**
* Description: 管理员业务处理层<br/>
* @author: wuwenguang<br/>
* @date: 2020/4/9 17:25<br/>
* @param:<br/>
* @return:
*/
public interface AdminService {
    /**
    * Description: 分页查询所有的用户信息<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/9 20:13<br/>
    * @param:<br/>
    * @return:
    */
    public PageResult queryAllLoginInfo(LoginInfoDto loginInfoDto);

    /**
    * Description: 修改用户信息<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/9 20:13<br/>
    * @param:<br/>
    * @return:
    */
    public int updateUser(LoginInfoDto loginInfoDto) throws Exception;

    /**
    * Description: 新增用户<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/9 20:31<br/>
    * @param:<br/>
    * @return:
    */
    public int insertUser(LoginInfoDto loginInfoDto) throws Exception;

    /**
    * Description: 分页查询图书信息<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/10 10:33<br/>
    * @param:<br/>
    * @return:
    */
    public PageResult queryBooksByPage(BookDto bookDto);

    /**
    * Description: 新增图书信息<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/10 10:33<br/>
    * @param:<br/>
    * @return:
    */
    public int insertBook(BookDto bookDto) throws Exception;

    /**
    * Description: 修改图书信息<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/10 10:37<br/>
    * @param:<br/>
    * @return:
    */
    public int updateBook(BookDto bookDto) throws Exception;

    /**
    * Description: 根据书本编号和归还状态查询未归还书籍借阅情况<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/10 14:52<br/>
    * @param:<br/>
    * @return:
    */
    public BorrowingInfo queryByBookNo(BorrowDto borrowDto);

    /**
    * Description: 归还图书<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/10 17:00<br/>
    * @param:<br/>
    * @return:
    */
    public int returnBook(BorrowDto borrowDto) throws Exception;

    /**
    * Description: 借阅图书<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/10 19:46<br/>
    * @param:<br/>
    * @return:
    */
    public int borrowBook(BorrowDto borrowDto) throws Exception;

    /**
    * Description: 续借图书<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/10 20:37<br/>
    * @param:<br/>
    * @return:
    */
    public int reNewBook(BorrowDto borrowDto) throws Exception;

    /**
    * Description: 查询待审核的书籍数量<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/11 23:22<br/>
    * @param:<br/>
    * @return:
    */
    public int queryNotCheck();

    /**
    * Description: 审核通过图书<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/12 11:02<br/>
    * @param:<br/>
    * @return:
    */
    public int passCheck(BookDto bookDto) throws Exception;

    /**
     * Description: 审核通过图书<br/>
     * @author: wuwenguang<br/>
     * @date: 2020/4/12 11:02<br/>
     * @param:<br/>
     * @return:
     */
    public int notPassCheck(BookDto bookDto) throws Exception;
}
