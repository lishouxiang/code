package com.example.demo;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author lishouxiang
 * @date 2022/5/1
 * @apiNote
 */
 class User1{
    String userName;
    int age;

    public User1(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

/**
 * 解决ABA问题？？？ 理解原子引用类+新增一种机制（那就是修改版本号，类似于时间戳）
 */
public class AtomicReferenceDemo {
    public static void main(String[] args) {

        User1 zs = new User1("zhangsan",22);
        User1 lisi = new User1("lisi",45);
        AtomicReference<User1> atomicReference = new AtomicReference<User1>();
        atomicReference.set(zs);
        System.out.println(atomicReference.compareAndSet(zs, lisi)+"\t"+atomicReference.get());
        System.out.println(atomicReference.compareAndSet(zs, lisi)+"\t"+atomicReference.get().toString());

    }
}
