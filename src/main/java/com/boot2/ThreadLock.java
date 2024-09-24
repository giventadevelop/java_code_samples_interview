package com.boot2;

public class ThreadLock {

    private static Thread thread1 ;



    private static final Thread1 thread1withVal = new Thread1();

    public static void main(String[] args) {

      thread1 =  new Thread(new Thread1());
        thread1.start();

      Thread thread2 =  new Thread(new Thread2());
        thread2.start();
    }

    public int getValue() throws InterruptedException {

       if (thread1withVal.getValue()> 10){
           return  thread1withVal.getValue();
       }else{
//           Thread.wait();
       }

        return 0;
    }

}
