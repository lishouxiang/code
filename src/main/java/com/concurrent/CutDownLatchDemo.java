package com.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

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
            }, CountryEnum.getCountryElement(i).getResMessage()).start();
        }
        cutDown.await();
        System.out.println(Thread.currentThread().getName()+"\t 秦灭六国，一统华夏");
    }
  
}
