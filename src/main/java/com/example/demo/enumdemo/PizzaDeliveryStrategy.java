package com.example.demo.enumdemo;

import com.example.demo.enumdemo.enumconstruct.Pizza;

/**
 * @author zhaoyu
 * @date 2020/8/24
 */
public enum PizzaDeliveryStrategy {
    EXPRESS {
        @Override
        public void deliver(Pizza pz) {
            System.out.println("Pizza will be delivered in express mode");
        }
    },
    NORMAL {
        @Override
        public void deliver(Pizza pz) {
            System.out.println("Pizza will be delivered in normal mode");
        }
    };

    public abstract void deliver(Pizza pz);
}
