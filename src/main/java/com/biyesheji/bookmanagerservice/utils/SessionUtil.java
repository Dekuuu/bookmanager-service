package com.biyesheji.bookmanagerservice.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


/*
 * 用户在业务层中获取Http请求、响应、session等
 */
@Component
public class SessionUtil {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	public void setSessionValue(String token,String object) {
//		设置过期时间
		redisTemplate.opsForValue().set(token,object,60*60,TimeUnit.SECONDS);
	}
	
	public String getSessionValue(String token) {
		return redisTemplate.opsForValue().get(token);
	}
}
