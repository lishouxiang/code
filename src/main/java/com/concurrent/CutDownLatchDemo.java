package com.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * @author lishouxiang
 * @date 2022/5/8
 * @apiNote
 */
public class CutDownLatchDemo {
    public static void main(String[] args) throws Exception {


      //  closeDoor();
        CountDownLatch cutDown = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"\t 国在，被灭");
                cutDown.countDown();
            }, String.valueOf(i)).start();
        }
        cutDown.await();
        System.out.println(Thread.currentThread().getName()+"\t 秦灭六国，一统华夏");
    }
  
    public static void closeDoor() throws InterruptedException {
        CountDownLatch cutDown = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"\t 上完自习，离开教室走人");
                cutDown.countDown();
            }, String.valueOf(i)).start();
        }
         cutDown.await();
        System.out.println(Thread.currentThread().getName()+"\t 88班长锁门，走人");
    }
}
