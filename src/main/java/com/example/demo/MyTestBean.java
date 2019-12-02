package com.example.demo;

import org.springframework.stereotype.Component;

/**
 * @author zhaoyu
 * @date 2019-02-19
 */
@Component
public class MyTestBean {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
