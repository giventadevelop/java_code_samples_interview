package com.boot2.producerconsumer;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Classic producer–consumer shared buffer using {@code synchronized} +
 * {@code wait}/{@code notifyAll} (manual approach from
 * <a href="https://www.youtube.com/watch?v=ITPesAZFvWI">JavaTechie — Producer Consumer</a>).
 *
 * <p>Producer puts integers into a bounded {@link LinkedList}; consumer takes them.
 * When the queue is full the producer waits; when empty the consumer waits.
 * After each successful put/take, {@code notifyAll} wakes the other side.</p>
 *
 * <p>See {@link ProducerConsumerUsingBlockingQueue} for the recommended
 * {@link java.util.concurrent.ArrayBlockingQueue} alternative that hides this locking.</p>
 */
public class ProducerConsumerExample {

    /** Max items the shared buffer may hold. */
    private final int capacity;

    /** Shared buffer (not thread-safe by itself — guarded by synchronized methods). */
    private final Queue<Integer> queue = new LinkedList<>();

    public ProducerConsumerExample(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Producer: block while full, then enqueue and notify waiting consumers.
     * Must be {@code synchronized} so wait/notify use this instance's monitor.
     */
    public synchronized void produce(int value) throws InterruptedException {
        // Wait until there is space in the queue
        while (queue.size() == capacity) {
            wait();
        }
        queue.offer(value);
        System.out.println(Thread.currentThread().getName() + " produced " + value
                + " (size=" + queue.size() + ")");
        // Wake all waiting consumers (and any other producers)
        notifyAll();
    }

    /**
     * Consumer: block while empty, then dequeue and notify waiting producers.
     */
    public synchronized int consume() throws InterruptedException {
        // Wait until there is an item to consume
        while (queue.isEmpty()) {
            wait();
        }
        int value = queue.poll();
        System.out.println(Thread.currentThread().getName() + " consumed " + value
                + " (size=" + queue.size() + ")");
        notifyAll();
        return value;
    }

    /**
     * Demo: one producer (faster) and one consumer (slower) so the buffer fills
     * and you can observe blocking via wait/notify.
     */
    public static void main(String[] args) throws InterruptedException {
        ProducerConsumerExample pc = new ProducerConsumerExample(5);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    pc.produce(i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "producer");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    pc.consume();
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "consumer");

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println("wait/notify demo done");
    }
}
