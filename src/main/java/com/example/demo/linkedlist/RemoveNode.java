package com.example.demo.linkedlist;

/**
 * @author zhaoyu
 * @date 2019-02-27
 */
public class RemoveNode {
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode(int x) { val = x; }
     * }
     */
    class Solution {
        public void deleteNode(ListNode node) {
            ListNode head = node;
            int val = node.val;
            while (head.next != null) {
                ListNode next = head.next;
                if (next.val == val) {
                    head.next = next.next;
                    next = null;
                }
            }
        }
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        int length = 0;

        while (head != null) {
            length++;
            head = head.next;
        }

        head = dummy;
        length -= n;

        while (length > 0) {
            length--;
            head = head.next;
        }
        head.next = head.next.next;
        return dummy.next;
    }

    public ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        ListNode first = dummy;
        ListNode second = dummy;

        for (int i = 1; i <= n + 1; i++) {
            first = first.next;
        }

        while (first != null) {
            first = first.next;
            second = second.next;
        }

        second.next = second.next.next;
        return dummy.next;

    }

    public ListNode reverseList(ListNode head) {
        ListNode dummy = null;
        while (head != null) {
            ListNode node = new ListNode(head.val);
            node.next = dummy;
            dummy = node;
            head = head.next;
        }
        head = dummy;
        return head;
    }

    public ListNode reverseList2(ListNode head) {
        if(head==null||head.next==null) return head;
        ListNode newHead=reverseList(head.next);
        head.next.next=head;
        head.next=null;
        return newHead;
    }

    public ListNode reverseList3(ListNode head) {

        ListNode pre = null;
        ListNode cur = head;
        ListNode next = null;   // 下一个节点

        while (cur != null) {
            // 拿到原来链表head的下一个节点
            next = cur.next;
            // 把当前链表的下一个节点指向上一个节点也就是pre
            cur.next = pre;
            // 重置pre为当前链表节点
            pre = cur;
            // 重置当前节点
            cur = next;
        }
        // 返回反转后的链表 也就是pre 其实就是cur
        return pre;
    }
}
