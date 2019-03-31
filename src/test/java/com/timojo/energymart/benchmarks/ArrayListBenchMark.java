package com.timojo.energymart.benchmarks;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;

public class ArrayListBenchMark {
    @Warmup(iterations = 4)
    @Measurement(iterations = 4)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public List<Integer> addToListWithDefaultInitializer() {
        List<Integer> intList = new ArrayList<>();
        for (int i = 0; i < 10_000; i++)
            intList.add(i);

        return intList;
    }

    @Warmup(iterations = 4)
    @Measurement(iterations = 4)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public List<Integer> addToListWithSetInitialCapacity() {
        List<Integer> intList = new ArrayList<>(10_000);
        for (int i = 0; i < 10_000; i++)
            intList.add(i);

        return intList;
    }
}
