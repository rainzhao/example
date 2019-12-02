package com.example.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationBeanFactoryMetadata;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;

/**
 * @author zhaoyu
 * @date 2019-02-19
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware, InitializingBean {
    private ApplicationContext applicationContext;
    private ConfigurationBeanFactoryMetadata beanFactoryMetadata;

    private Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        try {
            ConfigurationProperties cp = getAnnotation(bean, beanName, ConfigurationProperties.class);
            String prefix = "";
            if (cp != null) {
                prefix = StringUtils.isEmpty(cp.prefix()) ? cp.value() : cp.prefix();
            }
            return Binder.get(this.environment)
                    .bind(prefix, bean.getClass()).get();
        }catch (Exception ex) {

        }
        return bean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.beanFactoryMetadata = this.applicationContext.getBean(ConfigurationBeanFactoryMetadata.BEAN_NAME,
                ConfigurationBeanFactoryMetadata.class);
        this.environment = this.applicationContext.getEnvironment();
    }

    private <A extends Annotation> A getAnnotation(Object bean, String beanName, Class<A> type) {
        A annotation = this.beanFactoryMetadata.findFactoryAnnotation(beanName, type);
        if (annotation == null) {
            annotation = AnnotationUtils.findAnnotation(bean.getClass(), type);
        }
        return annotation;
    }

}
