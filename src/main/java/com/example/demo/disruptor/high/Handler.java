package com.example.demo.disruptor.high;


import com.lmax.disruptor.EventHandler;

/**
 * @author zhaoyu
 * @date 2019-02-28
 */
public class Handler implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        System.out.println(" handler process " + trade.getName());
    }
}
