package com.example.demo.linkedlist;

import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhaoyu
 * @date 2019-03-19
 */
public class CreateLinkedList {

    /**
     * 递归创建链表
     *
     * @param list
     * @return
     */
    public Node createList(List<Integer> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        Node head = new Node(list.get(0));
        Node second = createList(list.subList(1, list.size()));
        head.setNext(second);
        return head;
    }

    /**
     * 递归翻转链表
     *
     * @param head
     * @return
     */
    public Node reviseList(Node head) {
        if (head == null || head.getNext() == null) {
            return head;
        }
        Node newHead = reviseList(head.getNext());
        head.getNext().setNext(head);
        head.setNext(null);
        return newHead;
    }

    public Node reviseListSimple(Node head) {
        if (head == null || head.getNext() == null) {
            return head;
        }

        Node prev = null;
        Node next = null;
        Node cur = head;
        Node tail = null;
        while (cur != null) {
            next = cur.getNext();

            if (next == null) {
                tail = cur;
            }
            cur.setNext(prev);
            prev = cur;
            cur = next;
        }

        return tail;

    }

    public static void main(String[] args) {
        CreateLinkedList createLinkedList = new CreateLinkedList();
        Node list = createLinkedList.createList(Arrays.asList(1, 2, 3, 4, 5));
        Node.printList(list);

        Node node = createLinkedList.reviseListSimple(list);

        Node.printList(node);
    }
}
