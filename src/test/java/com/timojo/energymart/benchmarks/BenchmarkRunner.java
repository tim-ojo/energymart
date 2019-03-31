package com.timojo.energymart.benchmarks;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(ArrayListBenchMark.class.getSimpleName())
                .include(HashcodeBenchMark.class.getSimpleName())
                .include(ComparableHashmapBenchMark.class.getSimpleName())
                .forks(1)
                .jvmArgsAppend("-Djava.io.tmpdir=C:\\tmp")
                .build();

        new Runner(opt).run();
    }
}
