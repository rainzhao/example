package com.example.demo.design.chain.chainupgrade;

/**
 * @author: zhaoyu
 * @date: 2021/7/14
 * @description: 有两个方法，evaluate用于判断是否经过规则执行，execute用于执行具体的规则内容。
 */
public abstract class BasicRule<CORE_ITEM, T extends RuleContext<CORE_ITEM>> {

    public abstract boolean evaluate(T context);

    public abstract void execute(T context);

}
