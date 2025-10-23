package com.example.demo.quartz;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
public class QuartzConfig {

    /**
     * 1.創建 job
     */
    @Bean
    public JobDetailFactoryBean getJobDetailFactoryBean(){
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(MyJob.class);
        return  factory;
    }
    /**
     * 2.創建 Trigger
     */
    @Bean
    public SimpleTriggerFactoryBean getSimpleTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean){
        SimpleTriggerFactoryBean factory = new SimpleTriggerFactoryBean();
        //關聯JobDetail
        factory.setJobDetail(jobDetailFactoryBean.getObject());
        //重複間格時間(毫秒為單位)
        factory.setRepeatInterval(5000);
        //重複次數
        factory.setRepeatCount(4);

        return  factory;
    }
    /**
     * 3.創建 Scheduler
     */
    @Bean
    public SchedulerFactoryBean getSchedulerFactoryBean(SimpleTriggerFactoryBean simpleTriggerFactoryBean){
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setTriggers(simpleTriggerFactoryBean.getObject());
        return  factory;
    }
}
