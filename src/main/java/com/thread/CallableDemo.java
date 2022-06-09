package com.thread;


import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

class MyThread implements Runnable{

    @Override
    public void run() {

    }
}

class MyThread2 implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName()+"=========Come in Callable===");
          try {
                TimeUnit.SECONDS.sleep(3);
               } catch (Exception e) {
                e.printStackTrace();
            }
        return 1025;
    }
}

/**
 *多线程，第三种获得多线程的方式
 */
public class CallableDemo {

    public static void main(String[] args)  throws Exception{

        /**
         * 程序中有两个线程 一个main线程，另一个是AAAA线程
         */
        //FutureTask(Callable<V> callable)
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new MyThread2());
        FutureTask<Integer> futureTask01 = new FutureTask<Integer>(new MyThread2());

         new Thread(futureTask,"AAAA").start();
        new Thread(futureTask01,"BBBB").start();
       // int result01=futureTask.get();
        System.out.println(Thread.currentThread().getName()+"\t ********************88");

        int result =100;
//        while (!futureTask.isDone()){
//
//        }
       int result01=futureTask.get();
       // ;//建议放在最后，要求计算Callable的计算结果，如果没有计算完成就要强求，会导致堵塞，直到计算完成
        System.out.println("****************"+(result+result01));

    }
}
