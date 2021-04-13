package com.simple.juc;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZhuYX
 * @date 2021/04/13
 */
public class Bank {

    private final double[] account;
    private final Lock bankLock = new ReentrantLock();
    private final Condition sufficientFunds = bankLock.newCondition();

    public Bank(int n, double initialBalance) {
        account = new double[n];
        Arrays.fill(account, initialBalance);
    }

    public void transfer(int from, int to, double amount) {
        bankLock.lock();
        try {
            while (account[from] < amount) {
                sufficientFunds.await();
            }
            System.out.println(Thread.currentThread());
            account[from] -= amount;
            System.out.printf("%10.2f from %d to %d ", amount, from, to);
            account[to] += amount;
            System.out.printf(" Total Balance : %10.2f%n", getTotalBalance());
            sufficientFunds.signalAll();
        } catch (InterruptedException e) {
            System.out.println("########## msg : " + e.getMessage());
        } finally {
            bankLock.unlock();
        }
    }

    public double getTotalBalance() {
        bankLock.lock();
        try {
            double sum = 0;
            for (double a : account) {
                sum += a;
            }
            return sum;
        } finally {
            bankLock.unlock();
        }
    }

    public int size() {
        return account.length;
    }
}
