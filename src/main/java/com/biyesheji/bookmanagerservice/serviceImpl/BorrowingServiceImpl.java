package com.biyesheji.bookmanagerservice.serviceImpl;

import com.biyesheji.bookmanagerservice.Enum.ApiResultEnum;
import com.biyesheji.bookmanagerservice.dto.BorrowDto;
import com.biyesheji.bookmanagerservice.dto.PageResult;
import com.biyesheji.bookmanagerservice.entity.BorrowingInfo;
import com.biyesheji.bookmanagerservice.entity.BorrowingResult;
import com.biyesheji.bookmanagerservice.entity.LoginInfo;
import com.biyesheji.bookmanagerservice.mapper.BorrowingMapper;
import com.biyesheji.bookmanagerservice.service.AdminService;
import com.biyesheji.bookmanagerservice.service.BorrowingService;
import com.biyesheji.bookmanagerservice.service.LoginInfoService;
import com.biyesheji.bookmanagerservice.utils.EmailUtil;
import com.biyesheji.bookmanagerservice.utils.HttpServletRequestUtil;
import com.biyesheji.bookmanagerservice.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BorrowingServiceImpl implements BorrowingService {
    @Autowired
    private BorrowingMapper borrowingMapper;
    @Autowired
    private LoginInfoService loginInfoService;
    @Autowired
    EmailUtil emailUtil;
    @Autowired
    private AdminService adminService;

    @Override
    public PageResult queryBorrowingInfo(BorrowDto borrowDto) throws Exception{
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(),LoginInfo.class);
        if(loginInfo == null){
            throw new Exception("请先登录");
        }
        borrowDto.setBorrowingName(loginInfo.getUserName());
        List<BorrowingInfo> borrowingInfoList= borrowingMapper.queryBorrowingInfoByUserName(borrowDto);
        int count = borrowingMapper.queryBorrowingCountsByUserName(borrowDto);
        return new PageResult(borrowingInfoList,borrowDto.getStartIndex(),borrowDto.getEndIndex(),borrowDto.getPageSize(),borrowDto.getCurrentPage(),count);
    }

    @Override
    public void queryOverdue() {
        List<BorrowingInfo> borrowingInfoList = borrowingMapper.queryOverdueBorrowingInfo();
//        用HashMap存储每个用户逾期的书籍情况
        Map<String,Map<String,List<String>>> map =new HashMap();
        List<LoginInfo> users = loginInfoService.queryAllUsers();
        if(borrowingInfoList == null || users == null || borrowingInfoList.size()==0 || users.size()==0){
            return ;
        }
        for(LoginInfo loginInfo: users){
//            存储逾期的图书情况
            List<String> books = new ArrayList<>();
//            存储逾期列表和邮箱地址
            Map<String,List<String>> emailMap = new HashMap<>();
            for(BorrowingInfo borrowingInfo : borrowingInfoList){
                if(borrowingInfo.getBorrowingName().equals(loginInfo.getUserName())){
                    books.add(borrowingInfo.getBorrowingBookNoName());
                }
            }
            if(books.size() != 0){
                emailMap.put(loginInfo.getEmail(),books);
                map.put(loginInfo.getUserName(),emailMap);
            }
        }
        for(Map.Entry<String,Map<String,List<String>>> m : map.entrySet()){
            Map<String,List<String>> listTemp = m.getValue();
            for(Map.Entry<String,List<String>> mapTemp : listTemp.entrySet()){
                emailUtil.sendHtmlMail(mapTemp.getKey(),"图书借阅逾期信息",
                        "用户"+m.getKey()+"的逾期借阅图书有"+mapTemp.getValue()+"，请马上归还图书");
            }
        }
    }

    @Override
    public void queryBorrowing() {
        List<BorrowingInfo> borrowingInfoList = borrowingMapper.queryAllBrowingInfo();
//        用HashMap存储每个用户即将逾期的书籍情况
        Map<String,Map<String,List<String>>> map =new HashMap();
        List<LoginInfo> users = loginInfoService.queryAllUsers();
        if(borrowingInfoList == null || users == null || borrowingInfoList.size()==0 || users.size()==0){
            return ;
        }
        for(LoginInfo loginInfo: users){
//            存储即将逾期的图书情况
            List<String> books = new ArrayList<>();
//            存储即将逾期列表和邮箱地址
            Map<String,List<String>> emailMap = new HashMap<>();
            for(BorrowingInfo borrowingInfo : borrowingInfoList){
                if(borrowingInfo.getBorrowingName().equals(loginInfo.getUserName())){
                    books.add(borrowingInfo.getBorrowingBookNoName());
                }
            }
//            存在即将逾期的借阅记录才发送邮件
            if(books.size() != 0){
                emailMap.put(loginInfo.getEmail(),books);
                map.put(loginInfo.getUserName(),emailMap);
            }
        }
        for(Map.Entry<String,Map<String,List<String>>> m : map.entrySet()){
            Map<String,List<String>> listTemp = m.getValue();
            for(Map.Entry<String,List<String>> mapTemp : listTemp.entrySet()){
                emailUtil.sendHtmlMail(mapTemp.getKey(),"图书借阅即将逾期提醒信息",
                        "用户"+m.getKey()+"借阅的图书："+mapTemp.getValue()+"将于5天后逾期，请注意归还");
            }
        }
    }

    @Override
    public List<BorrowingResult> queryAllBrowingInfoByMonth() {
        return borrowingMapper.queryAllBrowingInfoByMonth();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int renew(BorrowDto borrowDto) throws Exception{
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(), LoginInfo.class);
        if(loginInfo == null){
            throw new Exception("请先登录");
        }
        borrowDto.setReturnState(ApiResultEnum.NOT_RETURN.getIndex());
//        先查
        BorrowingInfo borrowingInfo = borrowingMapper.queryByCondition(borrowDto);
        if(borrowingInfo == null){
            throw new Exception("该书籍已经归还!不能再被续借!!");
        }

//        判断是否已经不可续借，续借次数为0
        if(borrowingInfo.getRenewAble() == 0){
            throw new Exception("该书籍已被续借!不能再被续借!!");
        }
//        判断应归还时间是否已经逾期，如果逾期，则不允许续借
        if(borrowingInfo.getShouldReturnTime().getTime()<System.currentTimeMillis()){
            throw new Exception("已经逾期的借阅图书不能续借!");
        }
//        修改状态
//        这里时间要注意int类型的越界问题，两个数的加法默认走的是int类型
        long time =borrowingInfo.getShouldReturnTime().getTime()+1000*60*60*24*31L;
        borrowDto.setShouldReturnTime(new Date(time));
        borrowDto.setRenewAble(0);
        borrowingMapper.updateBorrowingInfo(borrowDto);
        return 1;
    }
}
