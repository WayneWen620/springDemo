package com.example.demo.quartz;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;


public class QuartzMain {

    public static void main(String[] args)throws SchedulerException {
        //1.創建JOB對象
        JobDetail job = JobBuilder.newJob(MyJob.class).build();
        //創建Trigget
        //1.簡單Trigger

//        Trigger trigger = TriggerBuilder.newTrigger()
//                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever())
//                .build();
        //2.corn Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/3 * * * * ?"))
                .build();

        //創建Schedule
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(job,trigger);

        //啟動Scheduler
        scheduler.start();
    }
}
