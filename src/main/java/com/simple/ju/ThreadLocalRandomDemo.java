package com.simple.ju;

import lombok.SneakyThrows;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhuYX
 * @date 2021/04/13
 */
public class ThreadLocalRandomDemo {

    /**
     *  threadLocalRandomProbe探针变量
     * 我们可以把threadLocalRandomProbe 理解为一个针对每个Thread的Hash值（不为0）
     * 它可以用来作为一个线程的特征值，基于这个值可以为线程在数组中找到一个特定的位置
     */
    @SneakyThrows
    public static void main(String[] args) {
        var current = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                System.out.println(current.nextInt());
            });
            t.start();
        }
        TimeUnit.SECONDS.sleep(10);
    }
}
