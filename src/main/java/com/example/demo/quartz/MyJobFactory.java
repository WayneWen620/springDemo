package com.example.demo.quartz;

import jakarta.annotation.Resource;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

@Component("jobFactory")
public class MyJobFactory extends AdaptableJobFactory {
    @Resource
    private AutowireCapableBeanFactory factory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
       Object jobInstance = super.createJobInstance(bundle);

        factory.autowireBean(jobInstance);

        return jobInstance;
    }
}
