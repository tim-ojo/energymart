package com.timojo.energymart.benchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.*;

public class HashcodeBenchMark {
    @State(Scope.Benchmark)
    public static class Data {
        HashMap<BadKey, Object> map1 = new HashMap<>();
        HashMap<GoodKey, Object> map2 = new HashMap<>();
        ArrayList<BadKey> badKeys;
        ArrayList<GoodKey> goodKeys;
        Random randomKey = new Random();

        @Setup
        public void setup() {
            final Object value = new Object();
            Random random = new Random();
            for (int i = 0; i < 10_000; i++) {
                int randInt = random.nextInt();
                map1.put(new BadKey(randInt, randInt+10), value);
                map2.put(new GoodKey(randInt, randInt+10), value);
            }

            badKeys = new ArrayList<>(map1.keySet());
            goodKeys = new ArrayList<>(map2.keySet());
        }
    }

    @Warmup(iterations = 4)
    @Measurement(iterations = 4)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void getWithBadHashcode(Data data, Blackhole blackhole) {
        BadKey randomBadKey = data.badKeys.get(data.randomKey.nextInt(data.badKeys.size()));
        HashMap<BadKey, Object> badKeyMap = data.map1;
        Object val = badKeyMap.get(randomBadKey);
        blackhole.consume(val);
    }

    @Warmup(iterations = 4)
    @Measurement(iterations = 4)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void getWithGoodHashcode(Data data, Blackhole blackhole) {
        GoodKey randomGoodKey = data.goodKeys.get(data.randomKey.nextInt(data.goodKeys.size()));
        HashMap<GoodKey, Object> goodKeyMap = data.map2;
        Object val = goodKeyMap.get(randomGoodKey);
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
    }

    private static class BadKey extends Key {
        public BadKey(int id, int categoryId) {
            super(id, categoryId);
        }

        @Override
        public int hashCode() {
            return id % 100;
        }
    }

    private static class GoodKey extends Key {
        public GoodKey(int id, int categoryId) {
            super(id, categoryId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, categoryId);
        }
    }
}
