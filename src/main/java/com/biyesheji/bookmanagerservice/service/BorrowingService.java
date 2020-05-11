package com.biyesheji.bookmanagerservice.service;

import com.biyesheji.bookmanagerservice.dto.BookDto;
import com.biyesheji.bookmanagerservice.dto.BorrowDto;
import com.biyesheji.bookmanagerservice.dto.PageResult;
import com.biyesheji.bookmanagerservice.entity.BorrowingInfo;
import com.biyesheji.bookmanagerservice.entity.BorrowingResult;

import java.util.List;

public interface BorrowingService {
    /**
    * Description: 根据用户名查询借阅情况<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/21 17:12<br/>
    * @param:<br/>
    * @return:
    */
    public PageResult queryBorrowingInfo(BorrowDto borrowDto) throws Exception;
    
    /**
    * Description: 查询所有的逾期借阅情况并发送邮件<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/22 23:17<br/>
    * @param:<br/>
    * @return:
    */
    public void queryOverdue();

    /**
    * Description: 查询所有的借阅情况<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/23 9:53<br/>
    * @param:<br/>
    * @return:
    */
    public void queryBorrowing();

    /**
    * Description: 查询当前月份热门书籍情况<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/24 10:54<br/>
    * @param:<br/>
    * @return:
    */
    public List<BorrowingResult> queryAllBrowingInfoByMonth();

    /**
    * Description: 续借图书<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/25 15:34<br/>
    * @param:<br/>
    * @return:
    */
    public int renew(BorrowDto borrowDto) throws Exception;
}
