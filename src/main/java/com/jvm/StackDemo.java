package com.jvm;

public class StackDemo {

    public static void testStack(){
        testStack();
    }

    public static void main(String[] args) {
//        testStack();
////        System.out.println("***i am method main...");
//        System.out.println(Runtime.getRuntime().maxMemory()/1024/1024);
//        byte bytrArry[] = new byte[1*1024*1024*4100800];
//        System.out.println("=========");
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        System.out.println("maxMemory"+maxMemory+"字节"+(maxMemory/(double)1024/1024)+"MB");
        System.out.println("totalMemory"+totalMemory+"字节"+(totalMemory/(double)1024/1024)+"MB");


    }
}
