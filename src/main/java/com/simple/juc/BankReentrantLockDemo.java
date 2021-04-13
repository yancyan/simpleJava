package com.simple.juc;

import lombok.SneakyThrows;

/**
 * @author ZhuYX
 * @date 2021/04/13
 */
public class BankReentrantLockDemo {

    public static final int NACCOUNTS = 100;
    public static final double INITIAL_BALANCE = 1000;
    public static final double MAX_AMOUNT = 1000;
    public static final int DELAY = 10;


    @SneakyThrows
    public static void main(String[] args) {
        Bank bank = new Bank(100, 1000);
        for (int i = 0; i < NACCOUNTS; i++) {
            int fromAccount = i;
            Runnable r = () -> {
                try {
                    while (true) {
                        int toAccount = (int) (bank.size() * Math.random());
                        double amount = MAX_AMOUNT * Math.random();
                        bank.transfer(fromAccount, toAccount, amount);
                        Thread.sleep((int) (DELAY * Math.random()));
                    }
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
            };
            Thread t = new Thread(r);//新建线程
            t.start();
        }

    }
}
