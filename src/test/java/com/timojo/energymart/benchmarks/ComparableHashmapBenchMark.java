package com.timojo.energymart.benchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ComparableHashmapBenchMark {
    @State(Scope.Benchmark)
    public static class Data {
        HashMap<Key, Object> map1 = new HashMap<>();
        HashMap<ComparableKey, Object> map2 = new HashMap<>();
        ArrayList<Key> keys;
        ArrayList<ComparableKey> comparableKeys;
        Random random = new Random();

        @Setup
        public void setup() {
            final Object value = new Object();
            Random random = new Random();
            for (int i = 0; i < 10_000; i++) {
                int randInt = random.nextInt();
                map1.put(new Key(randInt, randInt+10), value);
                map2.put(new ComparableKey(randInt, randInt+10), value);
            }

            keys = new ArrayList<>(map1.keySet());
            comparableKeys = new ArrayList<>(map2.keySet());
        }
    }

    @Warmup(iterations = 4)
    @Measurement(iterations = 4)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void getWithoutComparable(Data data, Blackhole blackhole) {
        Key randomKey = data.keys.get(data.random.nextInt(data.keys.size()));
        HashMap<Key, Object> keyMap = data.map1;
        Object val = keyMap.get(randomKey);
        blackhole.consume(val);
    }

    @Warmup(iterations = 4)
    @Measurement(iterations = 4)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void getWithComparable(Data data, Blackhole blackhole) {
        ComparableKey randomComparableKey = data.comparableKeys.get(data.random.nextInt(data.comparableKeys.size()));
        HashMap<ComparableKey, Object> comparableKeyMap = data.map2;
        Object val = comparableKeyMap.get(randomComparableKey);
        blackhole.consume(val);
    }

    private static class Key {
        int id;
        int categoryId;

        public Key(int id, int categoryId) {
            this.id = id;
            this.categoryId = categoryId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return id == key.id &&
                    categoryId == key.categoryId;
        }

        @Override
        public int hashCode() {
            return id % 10;
        }
    }

    private static class ComparableKey extends Key implements Comparable<Key> {
        public ComparableKey(int id, int categoryId) {
            super(id, categoryId);
        }

        @Override
        public int compareTo(Key otherKey) {
            int order = this.id - otherKey.id;
            if (order != 0) return order;
            return this.categoryId - otherKey.categoryId;
        }
    }
}
