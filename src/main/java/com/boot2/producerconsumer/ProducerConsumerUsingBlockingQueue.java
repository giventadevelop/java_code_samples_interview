package com.boot2.producerconsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Producer–consumer using {@link ArrayBlockingQueue} (recommended approach from
 * <a href="https://www.youtube.com/watch?v=ITPesAZFvWI">JavaTechie — Producer Consumer</a>).
 *
 * <h2>Why ArrayBlockingQueue?</h2>
 * In {@link ProducerConsumerExample} we manually:
 * <ul>
 *   <li>synchronize on a shared object</li>
 *   <li>{@code wait()} when full / empty</li>
 *   <li>{@code notifyAll()} after put / take</li>
 * </ul>
 * {@link ArrayBlockingQueue} is a <strong>bounded blocking queue backed by an array</strong>
 * that does that coordination internally. Producers and consumers only call
 * {@code put} / {@code take}; the queue blocks and wakes threads for you.
 *
 * <h2>ArrayBlockingQueue behavior (interview notes)</h2>
 * <ul>
 *   <li><strong>Bounded:</strong> capacity is fixed at construction (here {@code 5}).
 *       Once created, capacity cannot grow.</li>
 *   <li><strong>FIFO:</strong> insert at tail, remove from head.</li>
 *   <li><strong>Thread-safe:</strong> uses an internal lock (and conditions for not-full / not-empty).</li>
 *   <li><strong>{@code put(e)}:</strong> inserts, <em>blocking</em> if the queue is full
 *       until a consumer frees space — same idea as “while full → wait”.</li>
 *   <li><strong>{@code take()}:</strong> removes head, <em>blocking</em> if empty
 *       until a producer adds an item — same idea as “while empty → wait”.</li>
 *   <li><strong>Do NOT use {@code add}/{@code poll} for this pattern:</strong>
 *       {@code add} throws {@link IllegalStateException} when full;
 *       {@code poll} returns {@code null} when empty (no blocking).
 *       The video stresses preferring {@code put}/{@code take} so threads block correctly.</li>
 *   <li><strong>Optional fairness:</strong> {@code new ArrayBlockingQueue<>(n, true)}
 *       serves waiting threads roughly FIFO; default {@code false} favors throughput.</li>
 * </ul>
 *
 * <h2>put/take vs add/offer/poll (cheat sheet)</h2>
 * <pre>
 *  Operation   | Full queue              | Empty queue
 *  ------------|-------------------------|------------------
 *  put         | blocks                  | inserts
 *  offer       | returns false           | inserts
 *  add         | throws IllegalStateEx   | inserts
 *  take        | removes                 | blocks
 *  poll        | removes                 | returns null
 *  remove()    | removes                 | throws NoSuchElementEx
 * </pre>
 *
 * <p>Related wrapper demo: {@link com.boot2.BlockingQueue}.</p>
 */
public class ProducerConsumerUsingBlockingQueue {

    /**
     * Shared bounded buffer. Capacity 5 matches the video demo so the producer
     * can get ahead briefly and then block on {@code put} when full.
     */
    private static final int CAPACITY = 5;

    public static void main(String[] args) throws InterruptedException {
        /*
         * ArrayBlockingQueue handles locking + wait/notify internally.
         * We only need the shared queue reference for both threads.
         */
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(CAPACITY);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    /*
                     * put() BLOCKS if size == capacity until a consumer take()s.
                     * Do not use add(i) here — add() would throw when full instead of waiting.
                     */
                    queue.put(i);
                    System.out.println(Thread.currentThread().getName() + " produced " + i
                            + " (size=" + queue.size() + ")");
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "producer");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    /*
                     * take() BLOCKS if the queue is empty until a producer put()s.
                     * Do not use poll() here — poll() would return null when empty
                     * instead of waiting for the next item.
                     */
                    int value = queue.take();
                    System.out.println(Thread.currentThread().getName() + " consumed " + value
                            + " (size=" + queue.size() + ")");
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
        System.out.println("ArrayBlockingQueue demo done (final size=" + queue.size() + ")");
    }
}
