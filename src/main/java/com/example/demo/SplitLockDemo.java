package com.example.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SplitLockDemo {

    //原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();



    public void myLock() {

        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t come in......");
        while (!atomicReference.compareAndSet(null, thread)) {

        }

    }

    public void MyUnLock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "\t invock MyUnLock");
    }

    public static void main(String[] args) {

        SplitLockDemo splitLockDemo = new SplitLockDemo();
        new Thread(() -> {
            try {
                splitLockDemo.myLock();
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            splitLockDemo.MyUnLock();
        }, "AAA").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                splitLockDemo.myLock();
                 try {
                       TimeUnit.SECONDS.sleep(1);
                      } catch (Exception e) {
                       e.printStackTrace();
                   }
                splitLockDemo.MyUnLock();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "BBB").start();
    }
}
