package com.example.demo;

import org.springframework.stereotype.Service;

/**
 * @author zhaoyu
 * @date 2019-02-14
 */
@Service
public class YungPeople implements PeopleInfo {
    @Override
    public String getName() {
        return "YungName";
    }
}
