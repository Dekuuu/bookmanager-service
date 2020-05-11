package com.biyesheji.bookmanagerservice.mapper;

import com.biyesheji.bookmanagerservice.entity.CronConfig;

import javax.websocket.server.PathParam;

public interface CronConfigMapper {
    public CronConfig queryCronConfigByKey(@PathParam("keyValue") String key);
}
