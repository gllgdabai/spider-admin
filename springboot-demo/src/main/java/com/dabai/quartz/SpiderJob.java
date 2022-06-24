package com.dabai.quartz;

import com.dabai.spider.service.ISpiderMissionService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author
 * @create 2022-06-22 12:53
 */
public class SpiderJob implements Job {

    @Autowired
    private ISpiderMissionService spiderMissionService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 查询所有的爬虫任务
//        spiderMissionService.selectSpiderMissionList();

    }
}
