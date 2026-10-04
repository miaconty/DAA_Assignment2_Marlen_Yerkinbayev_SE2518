package com.daa;

public class MinHeap {
    private int[] heap;
    private int size;

    public MinHeap() {
        this.heap = new int[10];
        this.size = 0;
    }

    public int size() {
        return size;
    }

    private void ensureCapacity() {
        Metrics.steps++;
        if (size == heap.length) {
            int[] oldHeap = heap;
            heap = new int[heap.length * 2];
            Metrics.steps++;
            for (int i = 0; i < size; i++) {
                Metrics.steps++;
                Metrics.moves++;
                heap[i] = oldHeap[i];
            }
        }
    }

    public void insert(int val) {
        ensureCapacity();
        Metrics.steps++;
        Metrics.moves++;
        heap[size] = val;
        size++;
        bubbleUp(size - 1);
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        Metrics.steps++;
        return heap[0];
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        Metrics.steps++;
        int min = heap[0];
        Metrics.steps++;
        Metrics.moves++;
        heap[0] = heap[size - 1];
        size--;
        if (size > 0) {
            bubbleDown(0);
        }
        return min;
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            Metrics.steps += 2;
            Metrics.comparisons++;
            if (heap[index] < heap[parentIndex]) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    private void bubbleDown(int index) {
        while (index * 2 + 1 < size) {
            int leftChild = index * 2 + 1;
            int rightChild = index * 2 + 2;
            int smallest = leftChild;

            Metrics.steps++;
            if (rightChild < size) {
                Metrics.steps++;
                Metrics.comparisons++;
                if (heap[rightChild] < heap[leftChild]) {
                    smallest = rightChild;
                }
            }

            Metrics.steps += 2;
            Metrics.comparisons++;
            if (heap[smallest] < heap[index]) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        Metrics.steps += 2;
        Metrics.moves += 3;
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}
