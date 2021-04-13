package com.simple.juc;

import java.util.concurrent.atomic.LongAccumulator;

/**
 * @author ZhuYX
 * @date 2021/04/13
 */
public class LongAccumulatorDemo {

    public static void main(String[] args) {
        var accumulator = new LongAccumulator((r, l) -> r * l, 1);

        accumulator.accumulate(12);

        System.out.println(accumulator.get());
    }
}
