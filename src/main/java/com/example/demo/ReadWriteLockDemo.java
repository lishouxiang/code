package com.example.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁,多个线程同时读一个资源类没有任何问题，为了满足并发量，读取，共享
 * 应该同时进行
 * 但是如果有一个线程进行写操作，就不应该由其他的线程来进行读写操作
 * 例如：
 * 写---写 互斥不共存
 * 写---读 互斥不共存
 * 读--读 不互斥共享
 *
 * 写操作：中间必须是一个完整的统一体，中间不允许被打断
 */

class MyCache {
    private volatile Map<String, Object> map = new HashMap<>();
    private ReentrantReadWriteLock readWriteLock =new ReentrantReadWriteLock();

    // 写入数据
    public void put(String key, Object value) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"号线程写入数据开始，key="+key);
            map.put(key, value);
            // 模拟程序执行过程，但是不会出现多次连续写
            try {
                TimeUnit.MICROSECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"号线程写入数据结束");
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    // 读出数据
    public void get(String key) {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"号线程读出数据开始");
            Object o = map.get(key);
            // 模拟程序执行过程，可以出现多次连续读
            try {
                TimeUnit.MICROSECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"号线程读出数据结束，key="+key);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i = 1; i <= 5; i++) {
            int tempInt = i;
            new Thread(() -> {
                myCache.put(tempInt+"",tempInt+"");
            }, String.valueOf(tempInt)).start();
        }
        for (int i = 1; i <=5; i++) {
            int tempInt =i;
            new Thread(() -> {
                myCache.get(tempInt+"");
            }, String.valueOf(tempInt)).start();
        }

    }
}
