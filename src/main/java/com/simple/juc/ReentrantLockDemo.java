package com.simple.juc;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZhuYX
 * @date 2021/04/13
 */
public class ReentrantLockDemo {
    public static String a;

    /**
     * 公平锁在获取state时，多了一步判断，CHL队列是否有其他等待线程，如果有且不是当前线程，那么当前线程获取锁直接失败，hasQueuedPredecessors
     * @param args
     */
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

        System.out.println("###############");

        lock.lock();
    }
}
