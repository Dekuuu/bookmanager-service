package com.biyesheji.bookmanagerservice.config;

import com.biyesheji.bookmanagerservice.Enum.PreEnum;
import com.biyesheji.bookmanagerservice.entity.CronConfig;
import com.biyesheji.bookmanagerservice.service.BorrowingService;
import com.biyesheji.bookmanagerservice.service.CronConfigService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

/**
 * 定时任务配置类
 */
@Configuration
@Log4j2
public class CronConfig3 implements SchedulingConfigurer {
    @Autowired
    private CronConfigService cronConfigService;
    @Autowired
    private BorrowingService borrowingService;
    private CronConfig cronConfig;
    /**
    * Description: 每天凌晨1点更新，更新当月热门书籍借阅情况<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/22 21:56<br/>
    * @param:<br/>
    * @return:
    */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(
//                定时任务执行的内容，无论任务是否开启都需每隔一段时间执行
                () -> {
                    ifOpen();
                },
                triggerContext -> {
                    //                    数据库中获取定时任务的执行时间
                    cronConfig = cronConfigService.queryCronConfig(PreEnum.POPULAR_BOOKS.getValue());
//                    时间校验
                    if (cronConfig.getExcuteTime() == null || "".equals(cronConfig.getExcuteTime())) {
                        log.info("定时时间格式有误!");
                    }
                    return new CronTrigger(cronConfig.getExcuteTime()).nextExecutionTime(triggerContext);
                });
    }

    public void ifOpen() {
        cronConfig = cronConfigService.queryCronConfig(PreEnum.POPULAR_BOOKS.getValue());
        if ("on".equals(cronConfig.getSwitchOn())) {
//            定时任务处于开启状态
            log.info("执行定时任务>>>>>>查询当月热门书籍情况");
            borrowingService.queryAllBrowingInfoByMonth();
        } else {
//            定时任务处于关闭的状态
            log.info("定时任务已经关闭!》》》》》》》查询当月热门书籍情况");
        }
    }
}
