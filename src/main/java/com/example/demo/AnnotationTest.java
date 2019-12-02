package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhaoyu
 * @date 2019-02-19
 */
@Component
public class AnnotationTest {
    private final MyTestBean myTestBean;

    @Autowired
    public AnnotationTest(MyTestBean myTestBean) {
        this.myTestBean = myTestBean;
    }

}
