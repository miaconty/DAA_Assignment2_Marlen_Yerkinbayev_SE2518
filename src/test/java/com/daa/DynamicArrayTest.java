package com.daa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DynamicArrayTest {
    @Test
    void testAddAndGet() {
        DynamicArray arr = new DynamicArray();
        for (int i = 0; i < 100; i++) arr.add(i);
        assertEquals(100, arr.size());
        assertEquals(50, arr.get(50));
    }

    @Test
    void testInsertAndRemove() {
        DynamicArray arr = new DynamicArray();
        arr.add(10);
        arr.add(30);
        arr.add(1, 20);
        assertEquals(20, arr.get(1));
        assertEquals(20, arr.remove(1));
        assertEquals(30, arr.get(1));
    }

    @Test
    void testExceptions() {
        DynamicArray arr = new DynamicArray();
        assertThrows(IndexOutOfBoundsException.class, () -> arr.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> arr.remove(-1));
    }
}
