package com.biyesheji.bookmanagerservice;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling                   //开启定时任务
@EnableSwagger2Doc                  //开启swagger
@MapperScan(value={"com.biyesheji.bookmanagerservice.mapper"})
public class BookmanagerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookmanagerServiceApplication.class, args);
    }

}