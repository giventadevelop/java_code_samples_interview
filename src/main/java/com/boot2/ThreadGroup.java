package com.boot2;

public class ThreadGroup  {

    public static void main(String[] args) {

      Thread thread1 =  new Thread(new Thread1());
        thread1.start();

        Thread thread2 =  new Thread(new Thread2());
        thread2.start();
    }

}
