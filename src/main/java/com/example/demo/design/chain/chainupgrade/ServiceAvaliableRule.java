package com.example.demo.design.chain.chainupgrade;

/**
 * @author: zhaoyu
 * @date: 2021/7/14
 * @description:
 */
public class ServiceAvaliableRule extends BasicRule<UserPortrait, UserPortraitRuleContext>{
    @Override
    public boolean evaluate(UserPortraitRuleContext context) {
        return true;
    }

    @Override
    public void execute(UserPortraitRuleContext context) {
        System.out.println("ServiceAvaliable execute..., ");
    }
}
