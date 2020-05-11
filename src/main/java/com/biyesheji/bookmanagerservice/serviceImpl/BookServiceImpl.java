package com.biyesheji.bookmanagerservice.serviceImpl;

import com.biyesheji.bookmanagerservice.Enum.ApiResultEnum;
import com.biyesheji.bookmanagerservice.dto.BookDto;
import com.biyesheji.bookmanagerservice.dto.PageResult;
import com.biyesheji.bookmanagerservice.entity.Book;
import com.biyesheji.bookmanagerservice.entity.LoginInfo;
import com.biyesheji.bookmanagerservice.mapper.BookDictMapper;
import com.biyesheji.bookmanagerservice.mapper.BookInfoMapper;
import com.biyesheji.bookmanagerservice.service.BookService;
import com.biyesheji.bookmanagerservice.utils.HttpServletRequestUtil;
import com.biyesheji.bookmanagerservice.utils.LIstToMap;
import com.biyesheji.bookmanagerservice.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public
class BookServiceImpl implements BookService {
    @Autowired
    private BookInfoMapper bookInfoMapper;
    @Autowired
    private BookDictMapper bookDictMapper;
    @Override
    public PageResult queryBooks(BookDto bookDto) {
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(), LoginInfo.class);
        List<Book> list = new ArrayList<>();
        if(loginInfo == null){
            return new PageResult(list,bookDto.getStartIndex(),bookDto.getEndIndex(),bookDto.getPageSize(),bookDto.getCurrentPage(),0);
        }
        bookDto.setDonaterName(loginInfo.getUserName());
        list=bookInfoMapper.queryBooksByPage(bookDto);

        List<Map<String,String>> listMap=bookDictMapper.queryBookDict();
        HashMap<String,String> hashMap= LIstToMap.listToMap(listMap);
//        类别编号中文转码
        list.stream().map(book ->{
            book.setCategoryNoName(hashMap.get(book.getCategoryNo()));
            return book;
        }).forEach(System.out :: println);

//        状态中文转码
        Map<Integer,String> stateMap = new HashMap<>();
        stateMap.put(ApiResultEnum.LENT.getIndex(),ApiResultEnum.LENT.getName());
        stateMap.put(ApiResultEnum.NOT_LEND.getIndex(),ApiResultEnum.NOT_LEND.getName());
        stateMap.put(ApiResultEnum.NOT_CHECK.getIndex(),ApiResultEnum.NOT_CHECK.getName());
        stateMap.put(ApiResultEnum.CHECK_FAILED.getIndex(),ApiResultEnum.CHECK_FAILED.getName());
        list.stream().map(book ->{
            book.setStateName(stateMap.get(book.getState()));
            return book;
        }).forEach(System.out :: println);

        int count = bookInfoMapper.queryBooksByPageCount(bookDto);
        return new PageResult(list,bookDto.getStartIndex(),bookDto.getEndIndex(),bookDto.getPageSize(),bookDto.getCurrentPage(),count);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int updateBook(BookDto bookDto) throws Exception{
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(), LoginInfo.class);
        if(loginInfo == null){
            throw new Exception("请先登录");
        }
        List<Book> books = bookInfoMapper.queryBookSingleByCondition(bookDto);
        if(books == null || books.size()==0){
            throw new Exception("该书籍不存在!");
        }
        Book book = books.get(0);
        if((-1) != bookDto.getImageUrl().lastIndexOf("\\")){
            String imgUrl = bookDto.getImageUrl().substring(bookDto.getImageUrl().lastIndexOf("\\")+1);
            bookDto.setImageUrl("image/"+imgUrl);
        }
        bookDto.setUpdateTime(new Date());
        bookDto.setVersion(book.getVersion()+1);
        try{
            return bookInfoMapper.updateBook(bookDto);
        }catch (Exception e){
            throw new Exception("修改失败");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int insertBook(BookDto bookDto) throws Exception{
//        先查询该书籍是否已经存在
        /*List<Book> books = bookInfoMapper.queryBookSingleByCondition(bookDto);
        if(books.size() > 0){
            throw new Exception("该书籍已存在!");
        }*/
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(), LoginInfo.class);
        if(loginInfo == null){
            throw new Exception("请先登录");
        }
        bookDto.setDonaterName(loginInfo.getUserName());
        bookDto.setVersion(1);
//        设置状态为待审核，等待后台管理人员审核
        bookDto.setState(ApiResultEnum.NOT_CHECK.getIndex());
        try{
            return bookInfoMapper.insertBook(bookDto);
        }catch (Exception e){
            throw new Exception("添加失败");
        }
    }

    @Override
    public PageResult queryBooksByGroup(BookDto bookDto) {
         List<Book> books = bookInfoMapper.queryBooksGroup(bookDto);

//         类别编号中文转码
        List<Map<String,String>> categoriesMap=bookDictMapper.queryBookDict();
        HashMap<String,String> hashMap= LIstToMap.listToMap(categoriesMap);
//        类别编号中文转码
        books.stream().map(book ->{
            book.setCategoryNoName(hashMap.get(book.getCategoryNo()));
            return book;
        }).forEach(System.out :: println);

//        设置每本书的可借阅数量
         List<Map<String,Integer>> listMap = bookInfoMapper.queryBooksGroupCount();
         Map<String, BigDecimal> map = LIstToMap.listToMapInteger(listMap);
         books.stream().map(book ->{
             book.setCounts(Integer.parseInt(map.get(book.getBookName()).toString()));
             return book;
         }).forEach(System.out :: println);

        int count = bookInfoMapper.queryBooksGroupCounts(bookDto);
        return new PageResult(books,bookDto.getStartIndex(),bookDto.getEndIndex(),bookDto.getPageSize(),bookDto.getCurrentPage(),count);
    }

    @Override
    public String updateImage(MultipartFile file) throws Exception{
        try{
            String uid = UUID.randomUUID().toString();
            String fileName = uid+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String dirc = "image/"+fileName;
            File f = new File("d:/Desktop/nginx/nginx-1.8.1/static/bookservice-web/image/",fileName);
            file.transferTo(f);
            return dirc;
        }catch(Exception e){
            throw new Exception("修改图片失败");
        }

    }

    @Override
    public String addImage(MultipartFile file) throws Exception {
        try{
            String uid = UUID.randomUUID().toString();
            String fileName = uid+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String dirc = "image/"+fileName;
            File f = new File("d:/Desktop/nginx/nginx-1.8.1/static/bookservice-web/image/",fileName);
            file.transferTo(f);
            return dirc;
        }catch (Exception e){
            throw new Exception("添加图片失败");
        }
    }

    @Override
    public int reCheck(BookDto bookDto) throws Exception {
//        先查询
        List<Book> books = bookInfoMapper.queryBookSingleByCondition(bookDto);
        if(books == null || books.size() == 0){
            throw new Exception("该书籍信息不存在!");
        }
        bookDto.setState(ApiResultEnum.NOT_CHECK.getIndex());
        return bookInfoMapper.updateBook(bookDto);
    }
}
