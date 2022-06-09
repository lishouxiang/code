package com.thread;


import lombok.Synchronized;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareData {

    private int number = 0;
    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public void increment() throws Exception {
        lock.lock();
        try {
            //判断 一定要用while 防止虚假唤醒
            while (number != 0) {

                //等待不能生产
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            //通知唤醒
            condition.signalAll();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() throws Exception {
        lock.lock();
        try {
            //判断
            while (number == 0) {

                //等待不能生产
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            //通知唤醒
            condition.signalAll();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


}

/**
 * 一个初始值的变量，两个线程对它进行操作，一个加 1 一个减 1
 * 生产者，消费者模式
 * <p>
 * 多线程开发口诀
 * 1. 高并发前提一定是高内聚，低耦合
 * 2.线程操纵资源类
 * <p>
 * 3.判断，干活，唤醒通知
 * <p>
 * 4.严防多线程下的虚假唤醒
 */
public class ProdConstumerDemo {


    public static void main(String[] args) {

        ShareData shareData = new ShareData();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    shareData.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    shareData.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();


        //=======================================



//        new Thread(() -> {
//            for (int i = 1; i <= 5; i++) {
//                try {
//                    shareData.increment();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "CC").start();
//
//        new Thread(() -> {
//            for (int i = 1; i <= 5; i++) {
//                try {
//                    shareData.decrement();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "DD").start();



    }
}
