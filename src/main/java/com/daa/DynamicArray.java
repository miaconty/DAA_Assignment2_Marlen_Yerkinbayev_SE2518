package com.daa;

public class DynamicArray {
    private int[] data;
    private int size;

    public DynamicArray() {
        this.data = new int[10];
        this.size = 0;
    }

    public int size() {
        return size;
    }

    private void ensureCapacity() {
        Metrics.steps++;
        if (size == data.length) {
            int[] oldData = data;
            data = new int[data.length * 2];
            Metrics.steps++;
            for (int i = 0; i < size; i++) {
                Metrics.steps++;
                Metrics.moves++;
                data[i] = oldData[i];
            }
        }
    }

    public void add(int element) {
        ensureCapacity();
        Metrics.steps++;
        Metrics.moves++;
        data[size++] = element;
    }

    public void add(int index, int element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        ensureCapacity();
        for (int i = size; i > index; i--) {
            Metrics.steps++;
            Metrics.moves++;
            data[i] = data[i - 1];
        }
        Metrics.steps++;
        Metrics.moves++;
        data[index] = element;
        size++;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Metrics.steps++;
        return data[index];
    }

    public int remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Metrics.steps++;
        int removed = data[index];
        for (int i = index; i < size - 1; i++) {
            Metrics.steps++;
            Metrics.moves++;
            data[i] = data[i + 1];
        }
        size--;
        return removed;
    }

    public boolean contains(int value) {
        for (int i = 0; i < size; i++) {
            Metrics.steps++;
            Metrics.comparisons++;
            if (data[i] == value) {
                return true;
            }
        }
        return false;
    }
}
