package com.biyesheji.bookmanagerservice.serviceImpl;

import com.biyesheji.bookmanagerservice.Enum.ApiResultEnum;
import com.biyesheji.bookmanagerservice.dto.BookDto;
import com.biyesheji.bookmanagerservice.dto.BorrowDto;
import com.biyesheji.bookmanagerservice.dto.LoginInfoDto;
import com.biyesheji.bookmanagerservice.dto.PageResult;
import com.biyesheji.bookmanagerservice.entity.Book;
import com.biyesheji.bookmanagerservice.entity.BorrowingInfo;
import com.biyesheji.bookmanagerservice.entity.LoginInfo;
import com.biyesheji.bookmanagerservice.mapper.BookDictMapper;
import com.biyesheji.bookmanagerservice.mapper.BookInfoMapper;
import com.biyesheji.bookmanagerservice.mapper.BorrowingMapper;
import com.biyesheji.bookmanagerservice.mapper.LoginInfoMapper;
import com.biyesheji.bookmanagerservice.service.AdminService;
import com.biyesheji.bookmanagerservice.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private LoginInfoMapper loginInfoMapper;
    @Autowired
    private BookInfoMapper bookInfoMapper;
    @Autowired
    private BookDictMapper bookDictMapper;
    @Autowired
    private BorrowingMapper borrowingMapper;
    @Autowired
    private EmailUtil emailUtil;

    @Override
    public PageResult queryAllLoginInfo(LoginInfoDto loginInfoDto) {
//        权限校验
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(), LoginInfo.class);
        if(loginInfo == null){
            return new PageResult(null,loginInfoDto.getStartIndex(),loginInfoDto.getEndIndex(),loginInfoDto.getPageSize(),loginInfoDto.getCurrentPage(),0);
        }
        if(loginInfo.getUserType() == ApiResultEnum.USER.getIndex()){
            return new PageResult(null,loginInfoDto.getStartIndex(),loginInfoDto.getEndIndex(),loginInfoDto.getPageSize(),loginInfoDto.getCurrentPage(),0);
        }

        List<LoginInfo> list = loginInfoMapper.queryByPage(loginInfoDto);
        Map<Integer,String> map = new HashMap<>();
        map.put(ApiResultEnum.USER.getIndex(),ApiResultEnum.USER.getName());
        map.put(ApiResultEnum.MANAGER.getIndex(),ApiResultEnum.MANAGER.getName());
        list.stream().map(l ->{
           l.setUserTypeName(map.get(l.getUserType()));
           return l;

        }).forEach(System.out :: println);
        int counts = loginInfoMapper.queryCountsByPage(loginInfoDto);
        return new PageResult(list,loginInfoDto.getStartIndex(),loginInfoDto.getEndIndex(),loginInfoDto.getPageSize(),loginInfoDto.getCurrentPage(),counts);
    }

    @Override
    public int updateUser(LoginInfoDto loginInfoDto) throws Exception{
        //        权限校验
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(), LoginInfo.class);
        if(loginInfo == null){
            throw new Exception("请先登录");
        }
        if(loginInfo.getUserType() == ApiResultEnum.USER.getIndex()){
            throw new Exception("你没有权限访问!");
        }

        if(loginInfoDto.getNewPsw()==null || loginInfoDto.getOriginPsw() == null || "".equals(loginInfoDto.getOriginPsw()) || "".equals(loginInfoDto.getNewPsw())){
            return 0;
        }
        if(!loginInfoDto.getNewPsw().equals(loginInfoDto.getOriginPsw())){
//            新密码和旧密码不一致
            loginInfoDto.setNewPsw(MD5Util.encode(loginInfoDto.getNewPsw()));
        }
        loginInfoDto.setUpdateTime(new Date());
        return loginInfoMapper.updateLoginInfo(loginInfoDto);
    }

    @Override
    public int insertUser(LoginInfoDto loginInfoDto) throws Exception{
        //        权限校验
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(), LoginInfo.class);
        if(loginInfo == null){
            throw new Exception("请先登录");
        }
        if(loginInfo.getUserType() == ApiResultEnum.USER.getIndex()){
            throw new Exception("你没有权限访问!");
        }

//        先查询是否已经存在相同的记录
        List<LoginInfo> list= loginInfoMapper.queryByName(loginInfoDto.getUserName());
        if(list != null && list.size() > 0){
//            已存在记录
            return 0;
        }
        loginInfoDto.setPassword(MD5Util.encode(loginInfoDto.getPassword()));
        loginInfoDto.setLoginState(ApiResultEnum.NOT_LOGIN.getIndex());
        loginInfoDto.setVersion(1);
        return loginInfoMapper.insertLoginInfo(loginInfoDto);
    }

    @Override
    public PageResult queryBooksByPage(BookDto bookDto) {
        //        权限校验
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(), LoginInfo.class);
        if(loginInfo == null){
            return new PageResult(null,bookDto.getStartIndex(),bookDto.getEndIndex(),bookDto.getPageSize(),bookDto.getCurrentPage(),0);
        }
        if(loginInfo.getUserType() == ApiResultEnum.USER.getIndex()){
            return new PageResult(null,bookDto.getStartIndex(),bookDto.getEndIndex(),bookDto.getPageSize(),bookDto.getCurrentPage(),0);
        }

        List<Book> books = bookInfoMapper.queryBooksByPage(bookDto);
        int count = bookInfoMapper.queryBooksByPageCount(bookDto);

        List<Map<String,String>> listMap=bookDictMapper.queryBookDict();
        HashMap<String,String> hashMap= LIstToMap.listToMap(listMap);
//        类别编号中文转码
        books.stream().map(book ->{
            book.setCategoryNoName(hashMap.get(book.getCategoryNo()));
            return book;
        }).forEach(System.out :: println);

//        状态中文转码
        Map<Integer,String> stateMap = new HashMap<>();
        stateMap.put(ApiResultEnum.LENT.getIndex(),ApiResultEnum.LENT.getName());
        stateMap.put(ApiResultEnum.NOT_LEND.getIndex(),ApiResultEnum.NOT_LEND.getName());
        stateMap.put(ApiResultEnum.NOT_CHECK.getIndex(),ApiResultEnum.NOT_CHECK.getName());
        stateMap.put(ApiResultEnum.CHECK_FAILED.getIndex(),ApiResultEnum.CHECK_FAILED.getName());
        books.stream().map(book ->{
            book.setStateName(stateMap.get(book.getState()));
            return book;
        }).forEach(System.out :: println);

        return new PageResult(books,bookDto.getStartIndex(),bookDto.getEndIndex(),bookDto.getPageSize(),bookDto.getCurrentPage(),count);
    }

    @Override
    public int insertBook(BookDto bookDto) throws Exception{
        //        权限校验
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(), LoginInfo.class);
        if(loginInfo == null){
            throw new Exception("请先登录");
        }
        if(loginInfo.getUserType() == ApiResultEnum.USER.getIndex()){
            throw new Exception("你没有权限访问!");
        }

        bookDto.setVersion(1);
        bookDto.setState(ApiResultEnum.NOT_LEND.getIndex());
        bookDto.setDonaterName(loginInfo.getUserName());
        return bookInfoMapper.insertBook(bookDto);
    }

    @Override
    public int updateBook(BookDto bookDto) throws Exception{
        //        权限校验
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(), LoginInfo.class);
        if(loginInfo == null){
            throw new Exception("请先登录");
        }
        if(loginInfo.getUserType() == ApiResultEnum.USER.getIndex()){
            throw new Exception("你没有权限访问!");
        }

        bookDto.setUpdateTime(new Date());
        return bookInfoMapper.updateBook(bookDto);
    }

    @Override
    public BorrowingInfo queryByBookNo(BorrowDto borrowDto) {
        //        权限校验
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(), LoginInfo.class);
        if(loginInfo == null){
            throw null;
        }
        if(loginInfo.getUserType() == ApiResultEnum.USER.getIndex()){
            throw null;
        }

        borrowDto.setReturnState(ApiResultEnum.NOT_RETURN.getIndex());
        List<BorrowingInfo> borrowingInfoList = borrowingMapper.queryBorrowingInfoByUserName(borrowDto);
        if(borrowingInfoList == null || borrowingInfoList.size() == 0){
            return null;
        }
        return borrowingInfoList.get(0);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int returnBook(BorrowDto borrowDto) throws Exception{
        //        权限校验
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(), LoginInfo.class);
        if(loginInfo == null){
            throw new Exception("请先登录");
        }
        if(loginInfo.getUserType() == ApiResultEnum.USER.getIndex()){
            throw new Exception("你没有权限访问!");
        }

//        修改图书状态
        BookDto bookDto = new BookDto();
        bookDto.setBookNo(borrowDto.getBorrowingBookNo());
        bookDto.setState(ApiResultEnum.NOT_LEND.getIndex());
        bookInfoMapper.updateBook(bookDto);
//        修改借阅状态
        borrowDto.setReturnState(ApiResultEnum.NOT_RETURN.getIndex());
        borrowDto.setReturnStateNew(ApiResultEnum.RETURNED.getIndex());
        borrowDto.setReturnTime(new Date());
        borrowingMapper.updateBorrowingInfo(borrowDto);
        return 1;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int borrowBook(BorrowDto borrowDto) throws Exception{
        //        权限校验
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(), LoginInfo.class);
        if(loginInfo == null){
            throw new Exception("请先登录");
        }
        if(loginInfo.getUserType() == ApiResultEnum.USER.getIndex()){
            throw new Exception("你没有权限访问!");
        }

//        先校验借阅人信息
        List<LoginInfo> loginInfos = loginInfoMapper.queryByName(borrowDto.getBorrowingName());
        if(loginInfos == null || loginInfos.size() == 0){
            throw new Exception("该用户不存在!");
        }
//        修改图书状态
        BookDto bookDto = new BookDto();
        bookDto.setBookNo(borrowDto.getBorrowingBookNo());
        bookDto.setState(ApiResultEnum.LENT.getIndex());
        bookInfoMapper.updateBook(bookDto);
//        添加借阅信息
        borrowDto.setReturnState(ApiResultEnum.NOT_RETURN.getIndex());
        borrowDto.setRenewAble(1);
        borrowDto.setBorrowingTime(new Date());
        borrowDto.setShouldReturnTime(new Date(System.currentTimeMillis()+1000*60*60*24*30L));
        borrowDto.setVersion(1);
        borrowingMapper.insertBorrowInfo(borrowDto);
        return 1;
    }

    @Override
    public int reNewBook(BorrowDto borrowDto) throws Exception{
        //        权限校验
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(), LoginInfo.class);
        if(loginInfo == null){
            throw new Exception("请先登录");
        }
        if(loginInfo.getUserType() == ApiResultEnum.USER.getIndex()){
            throw new Exception("你没有权限访问!");
        }

        borrowDto.setReturnState(ApiResultEnum.NOT_RETURN.getIndex());
//        先查
        BorrowingInfo borrowingInfo = borrowingMapper.queryByCondition(borrowDto);
        if(borrowingInfo == null){
            return 0;
        }
//        判断是否已经不可续借，续借次数为0
        if(borrowingInfo.getRenewAble() == 0){
            throw new Exception("该书籍已被续借!不能再被续借!!");
        }
//        判断应归还时间是否已经逾期，如果逾期，则不允许续借
        if(borrowingInfo.getShouldReturnTime().getTime()<System.currentTimeMillis()){
            throw new Exception("已经逾期的借阅信息不能续借!");
        }
//        修改状态
//        这里时间要注意int类型的越界问题，两个数的加法默认走的是int类型
        long time =borrowingInfo.getShouldReturnTime().getTime()+1000*60*60*24*31L;
        borrowDto.setShouldReturnTime(new Date(time));
        borrowDto.setRenewAble(0);
        borrowingMapper.updateBorrowingInfo(borrowDto);
        return 1;
    }

    @Override
    public int queryNotCheck() {
        return bookInfoMapper.queryNotCheckCount();
    }

    @Override
    public int passCheck(BookDto bookDto) throws Exception{
        //        权限校验
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(), LoginInfo.class);
        if(loginInfo == null){
            throw new Exception("请先登录");
        }
        if(loginInfo.getUserType() == ApiResultEnum.USER.getIndex()){
            throw new Exception("你没有权限访问!");
        }

//        审核通过，初始化书本状态为未借出
        bookDto.setState(ApiResultEnum.NOT_LEND.getIndex());
        int i = bookInfoMapper.updateBook(bookDto);
//        发送邮件提醒用户
        if(i > 0){
            //        查询用户邮箱
            List<LoginInfo> list = loginInfoMapper.queryByName(bookDto.getDonaterName());
            if(list == null || list.size() == 0){
//                用户为空
                throw new Exception("该用户不存在!");
            }
            LoginInfo loginInfoL = list.get(0);
            emailUtil.sendHtmlMail(loginInfoL.getEmail(),"xxx图书信息管理系统图书审核结果提醒",
                    "用户"+loginInfoL.getUserName()+"您好，您于"+DateUtil.parseDateToStr(bookDto.getDonateTime(),DateUtil.DATE_FORMAT_YYYY_MM_DD)
                            +"捐赠的图书《"+
                    bookDto.getBookName()+"》已审核通过!");
        }
        return i;
    }

    @Override
    public int notPassCheck(BookDto bookDto) throws Exception{
        //        权限校验
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(), LoginInfo.class);
        if(loginInfo == null){
            throw new Exception("请先登录");
        }
        if(loginInfo.getUserType() == ApiResultEnum.USER.getIndex()){
            throw new Exception("你没有权限访问!");
        }

        //        审核不通过
        bookDto.setState(ApiResultEnum.CHECK_FAILED.getIndex());
        int i = bookInfoMapper.updateBook(bookDto);
//        发送邮件提醒用户
        if(i > 0){
            //        查询用户邮箱
            List<LoginInfo> list = loginInfoMapper.queryByName(bookDto.getDonaterName());
            if(list == null || list.size() == 0){
//                用户为空
                throw new Exception("该用户不存在!");
            }
            LoginInfo loginInfoL = list.get(0);
            emailUtil.sendHtmlMail(loginInfoL.getEmail(),"xxx图书信息管理系统图书审核结果提醒",
                    "用户"+loginInfoL.getUserName()+"您好，很抱歉，您于"+DateUtil.parseDateToStr(bookDto.getDonateTime(),DateUtil.DATE_FORMAT_YYYY_MM_DD)
                            +"捐赠的图书《"+
                            bookDto.getBookName()+"》审核不通过!");
        }
        return i;
    }
}
