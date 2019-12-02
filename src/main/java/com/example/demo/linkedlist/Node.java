package com.example.demo.linkedlist;

/**
 * @author zhaoyu
 * @date 2019-03-19
 */
public class Node {
    private final Integer val;

    private Node next;

    public Node(Integer val) {
        this.val = val;
        this.next = null;
    }

    public Integer getVal() {
        return val;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }


    public static void printList(Node head) {
        while(head != null) {
            System.out.print(head.getVal());
            head = head.getNext();
        }
        System.out.println();
    }
}
