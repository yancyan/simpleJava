package com.simple.juc;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author ZhuYX
 * @date 2021/04/14
 */
public class CountDownLatchDemo {
    public static final CountDownLatch LATCH = new CountDownLatch(5);

    @SneakyThrows
    public static void main(String[] args) {
        Executor e = Executors.newFixedThreadPool(8);


        for (int i = 0; i < 5; ++i) // create and start threads
            e.execute(new WorkerRunnable(LATCH, i));

        LATCH.await();           // wait for all to finish
        System.out.println("run end...");
    }

    static class WorkerRunnable implements Runnable{
        CountDownLatch latch;
        Integer count;
        public WorkerRunnable(CountDownLatch latch, Integer integer) {
            this.latch = latch;
            this.count = integer;
        }

        @Override
        public void run() {
            System.out.println("run count " + count);
            latch.countDown();
        }
    }
}
