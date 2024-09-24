package com.boot2;

public class Thread1 implements Runnable {

     private int threadVal;

    ThreadLock threadLock = new ThreadLock();
    @Override
    public void run() {


        try {
            threadLock.getValue();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < 10 ; i++) {

            if(i%2 == 0){

                System.out.println("Even Number Thread 1: "+ i);
                this.threadVal = i;
            }
        }

    }

    public int getValue(){

        return  this.threadVal;
    }

}
