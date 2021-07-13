package com.example.demo.enumdemo.enumconstruct;

import com.example.demo.enumdemo.PizzaDeliveryStrategy;
import com.example.demo.enumdemo.PizzaDeliverySystemConfiguration;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhaoyu
 * @date 2020/8/24
 */
public class Pizza {
    private PizzaStatus status;
    private static EnumSet<PizzaStatus> undeliveredPizzaStatuses =
            EnumSet.of(PizzaStatus.ORDERED, PizzaStatus.READY);

    public enum PizzaStatus {


        ORDERED(5) {
            @Override
            public boolean isOrdered() {
                return true;
            }
        },

        READY(2) {
            @Override
            public boolean isReady() {
                return true;
            }
        },

        DELIVERED(0) {
            @Override
            public boolean isDelivered() {
                return true;
            }
        };

        private int timeToDelivery;

        public boolean isOrdered() {
            return false;
        }

        public boolean isReady() {
            return false;
        }

        public boolean isDelivered() {
            return false;
        }

        public int getTimeToDelivery() {
            return timeToDelivery;
        }

        PizzaStatus(int timeToDelivery) {
            this.timeToDelivery = timeToDelivery;
        }

    }

    public boolean isDeliverable() {
        return this.status.isReady();
    }

    public boolean isOrdered() {
        return this.status.isOrdered();
    }

    public void printTimeToDeliver() {
        System.out.println("Time to delivery is " +
                this.getStatus().getTimeToDelivery());
    }

    public PizzaStatus getStatus() {
        return status;
    }

    public void setStatus(PizzaStatus status) {
        this.status = status;
    }

    public void givenPizaOrder_whenReady_thenDeliverable() {
        Pizza testPz = new Pizza();
        testPz.setStatus(PizzaStatus.ORDERED);
        boolean ordered = testPz.isOrdered();
        testPz.printTimeToDeliver();
        Assert.isTrue(testPz.isDeliverable(), "11");
    }

    public static List<Pizza> getAllUndeliveredPizzas(List<Pizza> input) {
        return input.stream().filter(
                (s) -> undeliveredPizzaStatuses.contains(s.getStatus()))
                .collect(Collectors.toList());
    }

    public void givenPizaOrder_whenDelivered_thenPizzaGetsDeliveredAndStatusChanges() {
        Pizza pz = new Pizza();
        pz.setStatus(Pizza.PizzaStatus.READY);
        pz.deliver();
        Assert.isTrue(pz.getStatus() == Pizza.PizzaStatus.DELIVERED);
    }

    public void givenPizaOrders_whenRetrievingUnDeliveredPzs_thenCorrectlyRetrieved() {
        List<Pizza> pzList = new ArrayList<>();
        Pizza pz1 = new Pizza();
        pz1.setStatus(Pizza.PizzaStatus.DELIVERED);

        Pizza pz2 = new Pizza();
        pz2.setStatus(Pizza.PizzaStatus.ORDERED);

        Pizza pz3 = new Pizza();
        pz3.setStatus(Pizza.PizzaStatus.ORDERED);

        Pizza pz4 = new Pizza();
        pz4.setStatus(Pizza.PizzaStatus.READY);

        pzList.add(pz1);
        pzList.add(pz2);
        pzList.add(pz3);
        pzList.add(pz4);

        List<Pizza> undeliveredPzs = Pizza.getAllUndeliveredPizzas(pzList);
        Assert.isTrue(undeliveredPzs.size() == 3);
    }

    public void deliver() {
        if (isDeliverable()) {
            PizzaDeliverySystemConfiguration.getInstance().getDeliveryStrategy()
                    .deliver(this);
            this.setStatus(PizzaStatus.DELIVERED);
        }
    }



    public static void main(String[] args) {

        Pizza pz = new Pizza();


        pz.givenPizaOrder_whenDelivered_thenPizzaGetsDeliveredAndStatusChanges();

    }
}
