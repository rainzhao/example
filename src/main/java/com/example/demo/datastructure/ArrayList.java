package com.example.demo.datastructure;

import java.util.HashMap;

/**
 * @author zhaoyu
 * @date 2019-07-25
 */
public class ArrayList<E> {

    private static final int DEFAULT_CAPACITY = 10;

    transient Object[] elementData;

    private int size;

    private int capacity;

    private static final Object[] EMPTY_ELEMENTDATA = {};

    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        }
    }

    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }




}
