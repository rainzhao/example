package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhaoyu
 * @date 2019-02-14
 */
@Component
public class PeopleTest {
    @Autowired
    public PeopleTest(List<PeopleInfo> peopleInfoList) {
        for (PeopleInfo peopleInfo : peopleInfoList) {
            System.out.println(peopleInfo.getName());
        }
    }
}
