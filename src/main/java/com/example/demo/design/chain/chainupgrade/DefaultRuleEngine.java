package com.example.demo.design.chain.chainupgrade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: zhaoyu
 * @date: 2021/7/14
 * @description:
 */
@Component
public class DefaultRuleEngine {

    @Resource
    private List<BasicRule> userRuleChain;

    public void invokeAll(RuleContext ruleContext) {
        for (BasicRule basicRule : userRuleChain) {
            if (basicRule.evaluate(ruleContext)) {
                basicRule.execute(ruleContext);
            }
        }
    }

}
