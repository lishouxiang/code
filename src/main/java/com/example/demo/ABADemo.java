package com.example.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author lishouxiang
 * @date 2022/5/1
 * @apiNote
 */
public class ABADemo {
    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {
        System.out.println("=======================以下是ABA问题的产生=======================");
        new Thread(() -> {
            try {
                atomicReference.compareAndSet(100, 101);//A=====>B
                atomicReference.compareAndSet(101, 100);//B=====>A
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            try {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // t2线程成功将值改成了自己想要的2019，但是t2线程并不知道t1线程的操作，t1线程已经完成了ABA操作
                System.out.println(atomicReference.compareAndSet(100, 2019) + "\t" + atomicReference.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2").start();
        //休息两秒，确保上面代码执行完毕
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("=======================以下是ABA问题的解决方案=======================");

        //t3线程暂停一秒
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();//t3线程同一时间获取版本号1
            System.out.println(Thread.currentThread().getName() + "\t 第一次获取的版本号是" + stamp);
            try {
                TimeUnit.SECONDS.sleep(1);
                atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
                System.out.println(Thread.currentThread().getName() + "\t 第二次获取的版本号是" + atomicStampedReference.getStamp());
                atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
                System.out.println(Thread.currentThread().getName() + "\t 第三次获取的版本号是" + atomicStampedReference.getStamp());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t3").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();//t4线程同一时间获取版本号1
            System.out.println(Thread.currentThread().getName() + "\t 第一次获取的版本号是" + stamp);

            //t4线程暂停3秒，目的是为了让上面线程t3执行完毕完成一次ABA操作
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //t4傻傻的认为一直没有改过
            boolean boolResult = atomicStampedReference.compareAndSet(100, 2019, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + "\t 修改成功否" + boolResult + "当前最新实际版本号" + atomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName() + "\t当前实际最新值" + atomicStampedReference.getReference());
        }, "t4").start();


    }
}
