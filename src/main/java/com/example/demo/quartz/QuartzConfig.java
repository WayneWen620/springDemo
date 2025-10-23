package com.example.demo.quartz;

import org.quartz.spi.JobFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
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
    public CronTriggerFactoryBean getSimpleTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean){
        CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
        //關聯JobDetail
        factory.setJobDetail(jobDetailFactoryBean.getObject());
        factory.setCronExpression("0/3 * * * * ?");
        return  factory;
    }
    /**
     * 3.創建 Scheduler
     */
    @Bean
    public SchedulerFactoryBean getSchedulerFactoryBean(CronTriggerFactoryBean simpleTriggerFactoryBean, JobFactory jobFactory){
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setTriggers(simpleTriggerFactoryBean.getObject());
        factory.setJobFactory(jobFactory);
        return  factory;
    }
}
