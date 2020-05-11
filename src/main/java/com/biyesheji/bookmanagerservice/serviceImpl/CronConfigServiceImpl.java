package com.biyesheji.bookmanagerservice.serviceImpl;

import com.biyesheji.bookmanagerservice.entity.CronConfig;
import com.biyesheji.bookmanagerservice.mapper.CronConfigMapper;
import com.biyesheji.bookmanagerservice.service.CronConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CronConfigServiceImpl implements CronConfigService {
    @Autowired
    private CronConfigMapper cronConfigMapper;
    @Override
    public CronConfig queryCronConfig(String key) {
        return cronConfigMapper.queryCronConfigByKey(key);
    }
}
