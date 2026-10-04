package com.daa;

public class MyLinkedList {
    private static class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public void add(int element) {
        Node newNode = new Node(element);
        Metrics.moves++;
        if (head == null) {
            head = newNode;
            tail = newNode;
            Metrics.moves += 2;
        } else {
            Metrics.moves++;
            tail.next = newNode;
            tail = newNode;
            Metrics.moves++;
        }
        size++;
    }

    public void add(int index, int element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == size) {
            add(element);
            return;
        }
        Node newNode = new Node(element);
        Metrics.moves++;
        if (index == 0) {
            newNode.next = head;
            head = newNode;
            Metrics.moves += 2;
        } else {
            Node curr = head;
            Metrics.steps++;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.next;
                Metrics.steps++;
            }
            newNode.next = curr.next;
            curr.next = newNode;
            Metrics.moves += 2;
        }
        size++;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node curr = head;
        Metrics.steps++;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
            Metrics.steps++;
        }
        return curr.val;
    }

    public int remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        int removedVal;
        if (index == 0) {
            Metrics.steps++;
            removedVal = head.val;
            head = head.next;
            Metrics.moves++;
            if (head == null) {
                tail = null;
                Metrics.moves++;
            }
        } else {
            Node curr = head;
            Metrics.steps++;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.next;
                Metrics.steps++;
            }
            removedVal = curr.next.val;
            Metrics.steps++;
            curr.next = curr.next.next;
            Metrics.moves++;
            if (index == size - 1) {
                tail = curr;
                Metrics.moves++;
            }
        }
        size--;
        return removedVal;
    }

    public boolean contains(int value) {
        Node curr = head;
        while (curr != null) {
            Metrics.steps++;
            Metrics.comparisons++;
            if (curr.val == value) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }
}
