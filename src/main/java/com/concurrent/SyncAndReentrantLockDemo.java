package com.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronized 与 ReentrantLock() 区别
 */

/**
 * 多线程之间顺序调用 A--->B---->C---D
 * <p>
 * A打印5次，B打印10次，C15次
 */

class ShareReSource {

    private int number = 1; //A:1 B:2 C:3
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    //1.判断
    public void print5() {
        lock.lock();
        try {
            while (number != 1) {
                c1.await();
            }
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            number = 2;
            c2.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public void print10() {
        lock.lock();
        try {
            while (number != 2) {
                c2.await();
            }
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            number = 3;
            c3.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15() {
        lock.lock();
        try {
            while (number != 3) {
                c3.await();
            }
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            number = 1;
            c1.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    //2.干活


    //3.通知


}

public class SyncAndReentrantLockDemo {

    public static void main(String[] args) {
        ShareReSource shareReSource = new ShareReSource();
        new Thread(() -> {
            for (int i = 1; i < 5; i++) {
                shareReSource.print5();
            }

        }, "A").start();
        new Thread(() -> {
            for (int i = 1; i < 10; i++) {
                shareReSource.print10();
            }

        }, "B").start();

        new Thread(() -> {
            for (int i = 1; i < 15; i++) {
                shareReSource.print15();
            }

        }, "C").start();

    }
}
