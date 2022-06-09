package com.queue;


import ch.qos.logback.core.util.TimeUtil;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyResource{

    private volatile boolean FLAG =true;//默认开始生产消费
    private AtomicInteger atomicInteger = new AtomicInteger();
    BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void myProd() throws Exception{

        String data = null;
        boolean returnValue;
        while (FLAG){
            data = atomicInteger.incrementAndGet()+"";
            returnValue = blockingQueue.offer(data,2L, TimeUnit.SECONDS);
            if(returnValue){
                System.out.println(Thread.currentThread().getName()+"\t"+"插入队列"+data+"成功");
            }else{
                System.out.println(Thread.currentThread().getName()+"\t"+"插入队列"+data+"失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName()+"\t"+"大老板叫停,生产结束 FLAG =false");
    }

    public void MyConsumers() throws Exception{

        String result =null;
        while (FLAG){
            result = blockingQueue.poll(2L,TimeUnit.SECONDS);
            if(result == null || result.equalsIgnoreCase("")){
                FLAG =false;
                System.out.println(Thread.currentThread().getName()+"\t"+"超过2S钟没有取到蛋糕,消费退出");
                System.out.println();
                System.out.println();
                System.out.println();
                return;
            }
            System.out.println(Thread.currentThread().getName()+"\t"+"消费对列"+result+"成功");

        }

    }
    public void stop() throws Exception{
        FLAG =false;
    }
}

/**
 * volatile/CAS/AtomicInteger/线程队列/
 */
public class ProductConsumterBlockQueueDemo {
    public static void main(String[] args) throws Exception{

        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(3));

          new Thread(() -> {
              System.out.println(Thread.currentThread().getName()+"\t 生产线程启动....");
              try {
                  myResource.myProd();
              } catch (Exception e) {
                  e.printStackTrace();
              }
          },"Prod").start();


        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t 消费线程启动....");
            System.out.println();
            System.out.println();
            try {
                myResource.MyConsumers();
                System.out.println();
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"Consumer").start();

         try {
               TimeUnit.SECONDS.sleep(5);
              } catch (Exception e) {
               e.printStackTrace();
           }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("5s时间到....大老板main叫停，结束");

        myResource.stop();

    }
}
