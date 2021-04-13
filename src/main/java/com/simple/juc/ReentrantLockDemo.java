package com.simple.juc;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZhuYX
 * @date 2021/04/13
 */
public class ReentrantLockDemo {

    @SneakyThrows
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        var locked = lock.tryLock(1000, TimeUnit.SECONDS);
        if (!locked) {
            return;
        }
        try {
            System.out.println("lock success.");

        }
        finally {
            lock.unlock();
        }
    }
}
