package com.example.demo.disruptor.quickstart;

import lombok.Data;

/**
 * @author zhaoyu
 * @date 2019-02-22
 */
@Data
public class OrderEvent {
    // 订单价格
    private long value;
}
