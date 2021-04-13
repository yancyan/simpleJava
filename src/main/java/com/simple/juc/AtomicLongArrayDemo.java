package com.simple.juc;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/**
 * @author ZhuYX
 * @date 2021/04/13
 */
public class AtomicLongArrayDemo {

    @Getter
    @Setter
    private volatile long x;

    public static void main(String[] args) {
        var atomicArr = new AtomicLongArray(24);
        atomicArr.getAndSet(1, 12);
        System.out.println(atomicArr.get(1));

        var xFiledUpdater = AtomicLongFieldUpdater.newUpdater(AtomicLongArrayDemo.class, "x");

        AtomicLongArrayDemo demo = new AtomicLongArrayDemo();

        xFiledUpdater.set(demo, 22L);
        System.out.println(demo.getX());
    }
}
