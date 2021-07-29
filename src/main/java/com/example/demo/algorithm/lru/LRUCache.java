package com.example.demo.algorithm.lru;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhaoyu
 * @date: 2021/7/13
 * @description:
 */
public class LRUCache {

    private Map<Integer, Entry> hashMap;

    private int capacity;

    private Entry head;

    private Entry tail;

    public LRUCache(int capacity) {
        initLRUCache();
        this.head = null;
        this.tail = null;
        this.capacity = capacity;
    }

    public void initLRUCache() {
        hashMap = new HashMap<>();
    }


    public int get(int key) {
        if (!hashMap.containsKey(key)) {
            return -1;
        }
        // 将数据前移
        Entry targetEntry = hashMap.get(key);
        moveToFirst(targetEntry);
        return hashMap.get(key).getValue();
    }

    public void put(int key, int value) {
        boolean exist = hashMap.containsKey(key);
        if (exist) {
            // 已经存在的节点直接移到最前
            Entry entry = hashMap.get(key);
            entry.setValue(value);
            moveToFirst(entry);
        } else {
            Entry entry = new Entry(key, value);
            if (hashMap.size() == capacity) {
                int lastkey = tail.getKey();
                if (hashMap.size() > 1) {
                    tail.pre.next = null;
                    tail = tail.pre;
                    tail.next = null;
                    hashMap.remove(lastkey);
                    addToFirst(entry);
                } else {
                    head = entry;
                    tail = entry;
                    hashMap.remove(lastkey);
                    hashMap.put(entry.getKey(), entry);
                }
            } else {
                if (hashMap.size() == 0) {
                    // 如果数组内缓存为空
                    head = entry;
                    tail = entry;
                    hashMap.put(key, entry);
                } else {
                    addToFirst(entry);
                }
            }
        }
    }


    private void addToFirst(Entry entry) {
        head.pre = entry;
        entry.next = head;
        entry.pre = null;
        head = entry;
        hashMap.put(entry.getKey(), entry);
    }

    /**
     * 已经存在的节点移动
     *
     * @param entry
     */
    private void moveToFirst(Entry entry) {
        if (hashMap.size() <= 1) {
            return;
        }
        if (entry.pre == null) {
            // 首节点不进行交换
            return;
        }

        if (entry.next == null) {
            // 尾部节点
            entry.pre.next = null;
            tail = entry.pre;
            entry.pre = null;
            entry.next = head;
            head.pre = entry;
            head = entry;
        } else {
            // 中间节点
            entry.pre.next = entry.next;
            entry.next.pre = entry.pre;
            entry.next = head;
            head.pre = entry;
            entry.pre = null;
            head = entry;
        }

    }

    class Entry {
        private Entry pre;
        private Entry next;
        private int key;
        private int value;

        public Entry(int key, int value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Entry getPre() {
            return pre;
        }

        public void setPre(Entry pre) {
            this.pre = pre;
        }

        public Entry getNext() {
            return next;
        }

        public void setNext(Entry next) {
            this.next = next;
        }
    }


    public static void main(String[] args) {
        int result = 0;

        //["LRUCache","put","get","put","get","get"]
        //[[1],[2,1],[2],[3,2],[2],[3]]

        LRUCache lRUCache = new LRUCache(1);
        lRUCache.put(2, 1); // {2=1}
        result = lRUCache.get(2); // {2=1}
        result = lRUCache.get(1);    // 返回
        lRUCache.put(3, 2); // {3=2}
        result = lRUCache.get(2);    // 返回 -1
        result = lRUCache.get(3);   // 返回 2
        System.out.println(result);
    }

}
