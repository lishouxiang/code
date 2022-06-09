package com.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore，俗称信号量 基于AbstractQueuedSynchronizer实现！AQS
 *
 *当线程要访问共享资源时，需要先通过acquire()方法获取许可。获取到之后许可就被当前线程占用了，在归还许可之前其他线程不能获取这个许可。
 *
 * 调用acquire()方法时，如果没有许可可用了，就将线程阻塞，等待有许可被归还了再执行。
 *
 * 当执行完业务功能后，需要通过release()方法将许可证归还，以便其他线程能够获得许可证继续执行。
 *
 * 如果初始化了一个许可为1的Semaphore，那么就相当于一个不可重入的互斥锁（Mutex）。
 *
 * 默认使用非公平的方式
 * sync = new NonfairSync(permits);
 *
 * 使用Semaphore可以控制同时访问资源的线程个数，例如，实现一个文件允许的并发访问数
 *
 */
public class SemaphoreDemo {
    public static void main(String[] args) {


        //Semaphore管理着一组许可permit，许可的初始数量通过构造函数设定。

        /**
         * Semaphore的主要方法摘要：
         *
         * 　　void acquire():从此信号量获取一个许可，在提供一个许可前一直将线程阻塞，否则线程被中断。
         *
         * 　　void release():释放一个许可，将其返回给信号量。
         *
         * 　　int availablePermits():返回此信号量中当前可用的许可数。
         *
         * 　　boolean hasQueuedThreads():查询是否有线程正在等待获取。
         */
        Semaphore semaphore = new Semaphore(3);

        for (int i = 1; i <=6 ; i++) {
                  new Thread(() -> {

                      try {
                          semaphore.acquire();
                          System.out.println(Thread.currentThread().getName()+"\t 抢到了车位");
                           try {
                                 TimeUnit.SECONDS.sleep(3);
                                } catch (Exception e) {
                                 e.printStackTrace();
                             }
                          System.out.println(Thread.currentThread().getName()+"\t 停车3s后离开");
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      }finally {
                          semaphore.release();
                      }

                  },String.valueOf(i)).start();
        }

    }
}



