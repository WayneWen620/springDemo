package com.example.demo.quartz;

import com.example.demo.service.TestQuartzService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 自訂義Job
 */
public class MyJob implements Job {

    @Autowired
    private TestQuartzService testQuartzService;
    //任務被觸發時執行
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("任務被執行:"+new Date());
        testQuartzService.hello();
    }
}
