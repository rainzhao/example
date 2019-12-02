package com.example.demo.array;

/**
 * @author zhaoyu
 * @date 2019-07-01
 */
public class ArrayList<T> {

    /**
     * 队首索引
     */
    private int front;

    /**
     * 队尾索引
     */
    private int tail;

    /**
     * 元素个数
     */
    private int count;

    /**
     * 数组
     */
    private Object[] data;

    public ArrayList(int size) {
        data = new Object[size];
    }

    /**
     * 添加一个元素
     */
    public void add(T e) {
        if (Math.abs(tail - front) % data.length == 0) {
            throw new IllegalArgumentException();
        }

        if (tail == data.length - 1) {

        }

        count++;
        data[tail] = e;


    }

    /**
     * 数组长度
     * @return
     */
    public int getSize() {
        return data.length;
    }

}
