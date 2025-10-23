package com.example.demo.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * 自訂義Job
 */
public class MyJob implements Job {
    //任務被觸發時執行
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("任務被執行:"+new Date());
    }
}
