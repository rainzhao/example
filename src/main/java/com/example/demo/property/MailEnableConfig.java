package com.example.demo.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaoyu
 * @date 2019-02-20
 */
@Configuration
@EnableConfigurationProperties(MailConfigProperty.class)
public class MailEnableConfig {

    private final MailConfigProperty mailConfigProperty;
    @Autowired
    public MailEnableConfig(MailConfigProperty mailConfigProperty) {
        this.mailConfigProperty = mailConfigProperty;
        System.out.println(mailConfigProperty.getUsername());
        System.out.println(mailConfigProperty.getPassword());
    }
}
