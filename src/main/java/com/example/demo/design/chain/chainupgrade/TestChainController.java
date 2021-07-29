package com.example.demo.design.chain.chainupgrade;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: zhaoyu
 * @date: 2021/7/14
 * @description:
 */
@RestController
public class TestChainController {

    @Resource
    private DefaultRuleEngine defaultRuleEngine;

    @GetMapping("/testUserChain")
    public void testUserChain() {
        UserPortraitRuleContext ruleContext = new UserPortraitRuleContext();
        UserPortrait userPortrait = new UserPortrait();
        userPortrait.setUsername("rainzhao");
        ruleContext.setData(userPortrait);
        ruleContext.setBasicInfo("basicInfo111....");
        defaultRuleEngine.invokeAll(ruleContext);
    }

}
