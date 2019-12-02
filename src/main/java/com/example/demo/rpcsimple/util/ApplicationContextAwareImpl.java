package com.example.demo.rpcsimple.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author zhaoyu
 * @date 2019-05-23
 *
 * 初始化 applicationContext
 */
@Component
public class ApplicationContextAwareImpl implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.applicationContext = applicationContext;
    }
}
