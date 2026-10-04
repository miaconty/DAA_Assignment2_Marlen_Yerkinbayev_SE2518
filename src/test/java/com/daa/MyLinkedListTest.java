package com.daa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyLinkedListTest {
    @Test
    void testAddAndGet() {
        MyLinkedList list = new MyLinkedList();
        for (int i = 0; i < 50; i++) list.add(i * 2);
        assertEquals(50, list.size());
        assertEquals(20, list.get(10));
    }

    @Test
    void testRemoveHeadAndTail() {
        MyLinkedList list = new MyLinkedList();
        list.add(1);
        list.add(2);
        assertEquals(1, list.remove(0));
        assertEquals(2, list.get(0));
    }

    @Test
    void testExceptions() {
        MyLinkedList list = new MyLinkedList();
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
    }
}
