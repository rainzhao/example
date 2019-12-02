package com.example.demo;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author zhaoyu
 * @date 2019-02-19
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ApolloConfiguration {
}
