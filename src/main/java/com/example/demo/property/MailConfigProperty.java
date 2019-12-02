package com.example.demo.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhaoyu
 * @date 2019-02-19
 */
@ConfigurationProperties(prefix = "mail")
public class MailConfigProperty {
    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
