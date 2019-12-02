package com.example.demo.taskrun;

import com.example.demo.threadpool.Callable;

/**
 * @author zhaoyu
 * @date 2019-08-17
 */
public class TaskAllSubmitTest {


    public void run() {
        TestA testA = new TestA();
        TestB testB = new TestB();
        ServiceTask<String> task1 = new ServiceTask<>(() -> testA.getName());
        ServiceTask<String> task2 = new ServiceTask<>(() -> testB.getName());

        ThreadPoolManager.submitAll(task1, task2).getResult();

        System.out.println(task1.getResult());
        System.out.println(task2.getResult());

    }

    public static void main(String[] args) {
        TaskAllSubmitTest taskAllSubmitTest = new TaskAllSubmitTest();
        taskAllSubmitTest.run();

        System.out.println("complete ....");
    }

    class TestA {

        private String name = "A";
        public String getName() {
            try {
                Thread.sleep(1000);
                return this.name;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.name;
        }
    }


    class TestB {
        private String name = "B";

        public String getName() {
            try {
                Thread.sleep(4000);
                return this.name;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.name;
        }
    }

}
