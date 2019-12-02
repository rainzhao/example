package com.example.demo;

import org.springframework.stereotype.Service;

/**
 * @author zhaoyu
 * @date 2019-02-14
 */
@Service
public class OldPeople implements PeopleInfo {
    @Override
    public String getName() {
        return "oldName";
    }
}
