package com.boot2;

public class Thread2 implements Runnable {

    ThreadLock threadLock = new ThreadLock();

    @Override
    public void run() {


        try {
            threadLock.getValue();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < 10 ; i++) {

            if(i%2 != 0){

                System.out.println("Odd Number Thread 2 : "+ i);
            }

        }

    }
}
