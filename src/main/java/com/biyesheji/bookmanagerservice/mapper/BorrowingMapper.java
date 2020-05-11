package com.biyesheji.bookmanagerservice.mapper;

import com.biyesheji.bookmanagerservice.dto.BorrowDto;
import com.biyesheji.bookmanagerservice.entity.BorrowingInfo;
import com.biyesheji.bookmanagerservice.entity.BorrowingResult;

import javax.websocket.server.PathParam;
import java.util.List;

public interface BorrowingMapper {
    /**
    * Description: 根据用户名查询对应用户的借阅信息<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/21 17:00<br/>
    * @param:<br/>
    * @return:
    */
    public List<BorrowingInfo> queryBorrowingInfoByUserName(BorrowDto borrowDto);

    /**
    * Description: 根据用户名查询用户借阅信息的总数<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/25 14:58<br/>
    * @param:<br/>
    * @return:
    */
    public int queryBorrowingCountsByUserName(BorrowDto borrowDto);
    /**
    * Description: 查询所有逾期借阅情况<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/22 23:19<br/>
    * @param:<br/>
    * @return:
    */
    public List<BorrowingInfo> queryOverdueBorrowingInfo();

    /**
    * Description: 查询所有的借阅情况--未逾期br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/23 9:56<br/>
    * @param:<br/>
    * @return:
    */
    public List<BorrowingInfo> queryAllBrowingInfo();

    /**
    * Description: 按当前月份查询当月热门书籍情况<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/24 10:48<br/>
    * @param:<br/>
    * @return:
    */
    public List<BorrowingResult> queryAllBrowingInfoByMonth();

    /**
    * Description: 修改借阅信息<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/25 15:36<br/>
    * @param:<br/>
    * @return:
    */
    public int updateBorrowingInfo(BorrowingInfo borrowingInfo);

    /**
    * Description: 添加借阅记录<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/10 19:49<br/>
    * @param:<br/>
    * @return:
    */
    public int insertBorrowInfo(BorrowDto borrowDto);

    /**
    * Description: 条件查询<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/10 21:11<br/>
    * @param:<br/>
    * @return:
    */
    public BorrowingInfo queryByCondition(BorrowDto borrowDto);
}
