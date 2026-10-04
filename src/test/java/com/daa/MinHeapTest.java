package com.daa;

import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class MinHeapTest {
    @Test
    void testMinHeapPropertyAndOrder() {
        MinHeap heap = new MinHeap();
        Random rand = new Random(42);
        for (int i = 0; i < 1000; i++) {
            heap.insert(rand.nextInt(10000));
        }

        int prev = Integer.MIN_VALUE;
        while (heap.size() > 0) {
            int current = heap.extractMin();
            assertTrue(current >= prev, "MinHeap property violated");
            prev = current;
        }
    }

    @Test
    void testEmptyHeapException() {
        MinHeap heap = new MinHeap();
        assertThrows(IllegalStateException.class, heap::peekMin);
        assertThrows(IllegalStateException.class, heap::extractMin);
    }
}
