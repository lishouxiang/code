package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lishouxiang
 * @date 2022/4/27
 * @apiNote
 *
 * 本讲主要介绍了 ThreadLocal 的两个典型的使用场景。
 *
 * 场景1，ThreadLocal 用作保存每个线程独享的对象，为每个线程都创建一个副本，每个线程都只能修改自己所拥有的副本, 而不会影响其他线程的副本，这样就让原本在并发情况下，线程不安全的情况变成了线程安全的情况。
 *
 * 场景2，ThreadLocal 用作每个线程内需要独立保存信息的场景，供其他方法更方便得获取该信息，每个线程获取到的信息都可能是不一样的，前面执行的方法设置了信息后，后续方法可以通过 ThreadLocal 直接获取到，避免了传参。
 *
 */
public class ThreadLocalDemo01 {

    public static void main(String[] args) {
        new Service1().service1();
    }
}

    class Service1{

        public void service1(){
            User user = new User("拉勾教育");
           UserContextHolder.holder.set(user);
            new Service2().service2();
        }

    }
    class Service2{
        public void service2(){
            User user = UserContextHolder.holder.get();
           System.out.println("Service2拿到用户名："+user.name);
            new Service3().service3();
        }

    }
    class Service3{
    public void service3(){

        User user = UserContextHolder.holder.get();
        System.out.println("Service3拿到用户名："+user.name);
        UserContextHolder.holder.remove();

}
    }

    class UserContextHolder {
        public static ThreadLocal<User> holder = new ThreadLocal<>();

    }

class User{
    String name;

    public User(String name) {
        this.name = name;
    }
}
