package com.dabai;

import org.junit.jupiter.api.Test;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author
 * @create 2022-06-22 10:54
 */
public class QuartzTests extends SpringbootDemoApplicationTests{

    @Autowired
    private Scheduler scheduler;

    @Test
    public void testDeleteJob() {
        try {
            boolean result = scheduler.deleteJob(new JobKey("testJob", "testJobGroup"));
            System.out.println("定时任务是否删除：" + result);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
