package com.daa;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Benchmark {
    private static final int[] SIZES = {100, 1000, 10000, 100000};
    private static final int RUNS = 5;

    public static void main(String[] args) throws Exception {
        File dir = new File("results");
        if (!dir.exists()) dir.mkdirs();

        try (PrintWriter writer = new PrintWriter(new File("results/results.csv"))) {
            writer.println("workload,variant,structure,n,time_ms,steps,moves,comparisons");

            for (int n : SIZES) {
                runW1(writer, n, "DynamicArray");
                runW1(writer, n, "MyLinkedList");

                runW2(writer, n, "DynamicArray");
                runW2(writer, n, "MyLinkedList");

                runW3(writer, n, "DynamicArray", "head");
                runW3(writer, n, "MyLinkedList", "head");
                runW3(writer, n, "DynamicArray", "middle");
                runW3(writer, n, "MyLinkedList", "middle");

                runW4(writer, n);
            }
        }
        System.out.println("Benchmark completed! Results saved to results/results.csv");
    }

    private static void runW1(PrintWriter writer, int n, String struct) {
        long[] times = new long[RUNS];
        long steps = 0, moves = 0, comps = 0;

        for (int r = 0; r < RUNS; r++) {
            Random rand = new Random(42);
            Metrics.reset();
            long start = System.nanoTime();

            if (struct.equals("DynamicArray")) {
                DynamicArray arr = new DynamicArray();
                for (int i = 0; i < n; i++) arr.add(rand.nextInt());
                Metrics.reset();
                for (int i = 0; i < 10000; i++) arr.get(rand.nextInt(n));
            } else {
                MyLinkedList list = new MyLinkedList();
                for (int i = 0; i < n; i++) list.add(rand.nextInt());
                Metrics.reset();
                for (int i = 0; i < 10000; i++) list.get(rand.nextInt(n));
            }

            times[r] = System.nanoTime() - start;
            if (r == RUNS - 1) {
                steps = Metrics.steps;
                moves = Metrics.moves;
                comps = Metrics.comparisons;
            }
        }
        Arrays.sort(times);
        double medianMs = times[RUNS / 2] / 1e6;
        writer.printf("W1,dash,%s,%d,%.4f,%d,%d,%d\n", struct, n, medianMs, steps, moves, comps);
    }

    private static void runW2(PrintWriter writer, int n, String struct) {
        long[] times = new long[RUNS];
        long steps = 0, moves = 0, comps = 0;

        for (int r = 0; r < RUNS; r++) {
            Random rand = new Random(42);
            int[] data = new int[n];
            for (int i = 0; i < n; i++) data[i] = rand.nextInt();

            Metrics.reset();
            long start = System.nanoTime();

            if (struct.equals("DynamicArray")) {
                DynamicArray arr = new DynamicArray();
                for (int val : data) arr.add(val);
                Metrics.reset();
                for (int i = 0; i < 500; i++) arr.contains(data[rand.nextInt(n)]);
                for (int i = 0; i < 500; i++) arr.contains(-rand.nextInt(1000000) - 1);
            } else {
                MyLinkedList list = new MyLinkedList();
                for (int val : data) list.add(val);
                Metrics.reset();
                for (int i = 0; i < 500; i++) list.contains(data[rand.nextInt(n)]);
                for (int i = 0; i < 500; i++) list.contains(-rand.nextInt(1000000) - 1);
            }

            times[r] = System.nanoTime() - start;
            if (r == RUNS - 1) {
                steps = Metrics.steps;
                moves = Metrics.moves;
                comps = Metrics.comparisons;
            }
        }
        Arrays.sort(times);
        double medianMs = times[RUNS / 2] / 1e6;
        writer.printf("W2,dash,%s,%d,%.4f,%d,%d,%d\n", struct, n, medianMs, steps, moves, comps);
    }

    private static void runW3(PrintWriter writer, int n, String struct, String variant) {
        long[] times = new long[RUNS];
        long steps = 0, moves = 0, comps = 0;

        for (int r = 0; r < RUNS; r++) {
            Random rand = new Random(42);
            Metrics.reset();
            long start = System.nanoTime();

            if (struct.equals("DynamicArray")) {
                DynamicArray arr = new DynamicArray();
                for (int i = 0; i < n; i++) arr.add(rand.nextInt());
                Metrics.reset();
                int idx = variant.equals("head") ? 0 : arr.size() / 2;
                for (int i = 0; i < 1000; i++) arr.add(idx, 99);
                for (int i = 0; i < 1000; i++) arr.remove(idx);
            } else {
                MyLinkedList list = new MyLinkedList();
                for (int i = 0; i < n; i++) list.add(rand.nextInt());
                Metrics.reset();
                int idx = variant.equals("head") ? 0 : list.size() / 2;
                for (int i = 0; i < 1000; i++) list.add(idx, 99);
                for (int i = 0; i < 1000; i++) list.remove(idx);
            }

            times[r] = System.nanoTime() - start;
            if (r == RUNS - 1) {
                steps = Metrics.steps;
                moves = Metrics.moves;
                comps = Metrics.comparisons;
            }
        }
        Arrays.sort(times);
        double medianMs = times[RUNS / 2] / 1e6;
        writer.printf("W3,%s,%s,%d,%.4f,%d,%d,%d\n", variant, struct, n, medianMs, steps, moves, comps);
    }

    private static void runW4(PrintWriter writer, int n) {
        long[] times = new long[RUNS];
        long steps = 0, moves = 0, comps = 0;

        for (int r = 0; r < RUNS; r++) {
            Random rand = new Random(42);
            Metrics.reset();
            long start = System.nanoTime();

            MinHeap heap = new MinHeap();
            for (int i = 0; i < n; i++) heap.insert(rand.nextInt());
            int prev = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                int curr = heap.extractMin();
                if (curr < prev) throw new IllegalStateException("Heap order violated!");
                prev = curr;
            }

            times[r] = System.nanoTime() - start;
            if (r == RUNS - 1) {
                steps = Metrics.steps;
                moves = Metrics.moves;
                comps = Metrics.comparisons;
            }
        }
        Arrays.sort(times);
        double medianMs = times[RUNS / 2] / 1e6;
        writer.printf("W4,dash,MinHeap,%d,%.4f,%d,%d,%d\n", n, medianMs, steps, moves, comps);
    }
}
