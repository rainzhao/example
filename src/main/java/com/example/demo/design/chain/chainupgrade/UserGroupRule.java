package com.example.demo.design.chain.chainupgrade;

/**
 * @author: zhaoyu
 * @date: 2021/7/14
 * @description:
 */
public class UserGroupRule extends BasicRule<UserPortrait, UserPortraitRuleContext> {

    @Override
    public boolean evaluate(UserPortraitRuleContext context) {
        return true;
    }

    @Override
    public void execute(UserPortraitRuleContext context) {
        UserPortrait userPortrait = context.getData();
        String basicInfo = context.getBasicInfo();
        System.out.printf("userGroupRule execute... basicInfo:%s, userInfo:%s%n", basicInfo,
                userPortrait.getUsername());
    }
}
