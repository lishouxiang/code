package com.example.demo;

import sun.misc.Unsafe;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Phone implements Runnable {
    public synchronized void SendSMS() throws Exception {
        System.out.println(Thread.currentThread().getId() + "\t invock SendSMS().....");
        SendEmail();


    }

    public synchronized void SendEmail() throws Exception {
        System.out.println(Thread.currentThread().getId() + "\t ###########invock SendEmail().....");
    }

    Lock lock = new ReentrantLock();


    @Override
    public void run() {
        get();

    }

    public void get() {
        try {
            lock.lock();
            lock.lock();
            System.out.println(Thread.currentThread().getId() + "\t invock get().....");
            set();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }

    public void set() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getId() + "\t ##########invock set().....");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class ReenterLockDemo {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> {
            try {
                phone.SendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1").start();


        new Thread(() -> {
            try {
                phone.SendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2").start();

         try {
               TimeUnit.SECONDS.sleep(1);
              } catch (Exception e) {
               e.printStackTrace();
           }

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        Thread t3 = new Thread(phone);
        Thread t4 = new Thread(phone);
        t3.start();
        t4.start();

    }
}
