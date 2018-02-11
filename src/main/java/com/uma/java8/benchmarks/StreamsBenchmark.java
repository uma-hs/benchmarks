package com.uma.java8.benchmarks;


import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by uhs on 11/2/18
 */

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 2, time=500, timeUnit= TimeUnit.MILLISECONDS)
@Measurement(iterations = 2, time=500, timeUnit=TimeUnit.MILLISECONDS)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class StreamsBenchmark {

    @State(Scope.Benchmark)
    public static class InputArrayContainer {

        @Param({ "10", "15", "20", "25", "30", "40", "60", "80", "100", "200", "400", "800"})
        int arraySize;

        int array[];

        int input[];

        @Setup(Level.Trial)
        public void initArray() {
            array = new int[arraySize];
            for(int i=0; i<arraySize; i++) {
                array[i] = new Random().nextInt(10000);
            }
        }

        @Setup(Level.Invocation)
        public void cloneArray() {
            input = array.clone();
        }

        public int[] getInputArray() {
            return input;
        }
    }


    @Benchmark
    public int[] sortArray(InputArrayContainer container) {
        return Arrays.stream(container.getInputArray()).sorted().toArray();
    }

    @Benchmark
    public int[] parallelStreamSort(InputArrayContainer container) {
        return Arrays.stream(container.getInputArray()).parallel().sorted().toArray();
    }

}
