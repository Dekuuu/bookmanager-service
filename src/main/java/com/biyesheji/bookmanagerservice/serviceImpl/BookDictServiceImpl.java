package com.biyesheji.bookmanagerservice.serviceImpl;

import com.biyesheji.bookmanagerservice.entity.BookDict;
import com.biyesheji.bookmanagerservice.mapper.BookDictMapper;
import com.biyesheji.bookmanagerservice.service.BookDictService;
import com.biyesheji.bookmanagerservice.utils.LIstToMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookDictServiceImpl implements BookDictService {
    @Autowired
    private BookDictMapper bookDictMapper;
    @Override
    public List<BookDict> queryAllBookDicts() {
        return bookDictMapper.queryBookDictEntity();
    }
}
