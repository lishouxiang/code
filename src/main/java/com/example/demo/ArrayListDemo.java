package com.example.demo;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @date 2022/5/1
 * @apiNote
 */

/**
 * 集合类ArrayList不安全问题出现原因以及解决方案
 */
public class ArrayListDemo {
    public static void main(String[] args) {
          //listNotSafe();
         //setNotSafe();
        //MapNotSafe();

    }

    public static void MapNotSafe() {
        Map<String,Object> map = new ConcurrentHashMap<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();

        }

        /**
         * 1.异常故障
         * java.util.ConcurrentModificationException
         *
         * 2.解决方案
         * 2.1 Collections.synchronizedMap(new HashMap<>());
         * 2.2 new ConcurrentHashMap<>()
         *
         */}

    public static void setNotSafe() {
        Set<String> set = new CopyOnWriteArraySet<>();
        //Collections.synchronizedSet(new HashSet<>());
        //new HashSet<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();

        }
        /**
         * 1.报错异常，故障现象
         * java.util.ConcurrentModificationException
         *
         * 2.解决方案
         * Collections.synchronizedSet(new HashSet<>());
         *
         * 3.底层代码
         *  private final CopyOnWriteArrayList<E> al;
         *  public CopyOnWriteArraySet() {
         *  al = new CopyOnWriteArrayList<E>();
         *  }
         *  可以看出 new CopyOnWriteArraySet<>() 底层
         *  也是使用了 new CopyOnWriteArrayList<E>();
         *
         *
         */
        new HashSet<>().add("c");
        /**
         * HashSet<>()的底层源码是
         *  public HashSet() {
         *         map = new HashMap<>();
         *     }
         * 那为什么set的add天添加方法是一个元素而不是两个元素？
         * 从源码可以看出 map的value是一个常量值 Object
         * private static final Object PRESENT = new Object();
         *  public boolean add(E e) {
         *         return map.put(e, PRESENT)==null;
         *     }
         *
         */}

    private static void listNotSafe() {
        List list = new CopyOnWriteArrayList();//写时复制，读写分离的思想
        Set set = new CopyOnWriteArraySet();
//        list.add("a");
//        list.add("b");
//        list.add("c");
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();

        }
        /**
         * 1.报错异常，故障现象
         * Exception in thread "20" java.util.ConcurrentModificationException
         *
         * 2.导致原因
         * 并发争抢导致，一个线程正在写，另一个线程过来抢夺，导致数据不一致异常，并发修改异常
         *
         *
         * 3.解决方案
         * 3.1  new Vector();
         * 3.2  Collections.synchronizedList(new ArrayList<>());
         * 3.3  new CopyOnWriteArrayList();
         *
         *
         * 4.优化建议
         */

        /**
         * copyOnWrite容器即写时复制容器，往一个容器添加元素的时候，不会直接往Object[] 去添加，而是先将Object[]容器进行copy
         * 复制出一个新的容器 Object[] elements,然后新的容器里Object[] elements 添加元素，添加完元素之后，再将原容器的指向，指向新的容器
         *  setArray(newElements) ，这样做的好处时并发对容器copyOnWrite读的时候不需要进行加锁，因为当前容器不会添加任何元素
         *  所以copyOnWrite容器也是一种读写分离的思想，都和写不同容器
         *
         *
         *   public boolean add(E e) {
         *         final ReentrantLock lock = this.lock;
         *         lock.lock();
         *         try {
         *             Object[] elements = getArray();
         *             int len = elements.length;
         *             Object[] newElements = Arrays.copyOf(elements, len + 1);
         *             newElements[len] = e;
         *             setArray(newElements);
         *             return true;
         *         } finally {
         *             lock.unlock();
         *         }
         *     }
         *
         *
         */}

}

