package com.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 *
 * 1.队列
 *
 *
 * 2.阻塞队列
 *  2.1阻塞队列有没有好的一面
 *  2.2不得不足阻塞你会如何管理
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws Exception{



      //  List list= new ArrayList<>();
        BlockingQueue<String> blockingQueue =new ArrayBlockingQueue<String>(3);
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("b", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("c", 2L, TimeUnit.SECONDS));

        /**
         * 等两秒钟后才可以插入，过时不候
         */
        System.out.println(blockingQueue.offer("d", 2L, TimeUnit.SECONDS));
    }
}
