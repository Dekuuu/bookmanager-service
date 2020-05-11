package com.biyesheji.bookmanagerservice.service;

import com.biyesheji.bookmanagerservice.entity.CronConfig;

public interface CronConfigService {
    public CronConfig queryCronConfig(String key);
}
