package com.example.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lishouxiang
 * @date 2022/4/25
 * @apiNote
 * CAS 是什么 比较并交换
 */
public class CASDemo {

    public static void main(String[] args) {

        AtomicInteger atomicInteger =new AtomicInteger(5);
        System.out.println(atomicInteger.compareAndSet(5, 2019)+"\t 当前值 "+atomicInteger.get());//true	 当前值 2019

        System.out.println(atomicInteger.compareAndSet(5, 1024)+"\t 当前值 "+atomicInteger.get());// false	 当前值 2019

        /**
         * Atomically increments by one the current value.
         * @return the previous value
         * //this 当前对象  valueOffset内存地址
         * public final int getAndIncrement () {
         * return unsafe.getAndAddInt(this, valueOffset, 1);
         *}
         *
         */

        AtomicInteger atomicInteger1 =new AtomicInteger(5);
        atomicInteger1.getAndIncrement();








    }

}
