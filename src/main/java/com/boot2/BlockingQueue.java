package com.boot2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Producer–consumer example built on {@link ArrayBlockingQueue}.
 *
 * <p>{@link ArrayBlockingQueue} is a bounded, thread-safe queue backed by an array.
 * When the queue is full, {@code put} blocks the producer until space is available.
 * When the queue is empty, {@code take} blocks the consumer until an element arrives.
 * That blocking behavior is what coordinates producers and consumers without busy-waiting
 * or manual {@code wait}/{@code notify} on a plain list.</p>
 *
 * <p>Earlier versions of this class used a plain {@code ArrayList} plus
 * {@code synchronized} methods. That was not a true blocking queue: {@code get}
 * did not wait for data, and {@code put} did not wait for capacity.</p>
 */
public class BlockingQueue {

    /** Default capacity when none is supplied. */
    private static final int DEFAULT_CAPACITY = 5;

    /** Singleton holder (lazy, thread-safe via synchronized {@link #getObject()}). */
    private static BlockingQueue instance;

    /**
     * Bounded blocking queue of work items.
     * Capacity limits how far producers can get ahead of consumers.
     */
    private final ArrayBlockingQueue<String> queue;

    /**
     * Creates a queue with the default capacity.
     * Private so callers go through {@link #getObject()} or {@link #create(int)}.
     */
    private BlockingQueue() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates a queue with an explicit capacity.
     *
     * @param capacity maximum number of elements the queue may hold; must be &gt; 0
     */
    private BlockingQueue(int capacity) {
        // ArrayBlockingQueue requires a positive capacity; fair=false uses non-fair locking
        // (generally higher throughput). Pass true as the second arg for FIFO lock fairness.
        this.queue = new ArrayBlockingQueue<>(capacity);
    }

    /**
     * Returns the shared singleton instance (default capacity).
     * Double-checked pattern is not used here; the whole factory is synchronized
     * so only one instance is created under concurrent first access.
     *
     * @return shared {@link BlockingQueue} wrapper
     */
    public static synchronized BlockingQueue getObject() {
        if (instance == null) {
            instance = new BlockingQueue();
        }
        return instance;
    }

    /**
     * Factory for a non-singleton instance with a custom capacity.
     * Useful in tests or demos that should not share global state.
     *
     * @param capacity bounded size of the underlying {@link ArrayBlockingQueue}
     * @return a new wrapper instance
     */
    public static BlockingQueue create(int capacity) {
        return new BlockingQueue(capacity);
    }

    /**
     * Producer API: inserts {@code item} into the queue, blocking if the queue is full
     * until a consumer frees a slot.
     *
     * <p>Uses {@link ArrayBlockingQueue#put(Object)}, which waits (does not spin)
     * when capacity is exhausted.</p>
     *
     * @param item value produced for later consumption; must not be {@code null}
     *             ({@link ArrayBlockingQueue} does not allow null elements)
     * @throws InterruptedException if the calling thread is interrupted while waiting
     */
    public void putObject(String item) throws InterruptedException {
        queue.put(item);
    }

    /**
     * Consumer API: removes and returns the head of the queue, blocking if empty
     * until a producer inserts an element.
     *
     * <p>Uses {@link ArrayBlockingQueue#take()}, which waits for data instead of
     * throwing or returning null when the queue has no elements.</p>
     *
     * @return the next available item (FIFO order)
     * @throws InterruptedException if the calling thread is interrupted while waiting
     */
    public String takeObject() throws InterruptedException {
        return queue.take();
    }

    /**
     * Convenience alias kept for callers of the older API name.
     * Prefer {@link #takeObject()} in new code.
     *
     * @return the next available item
     * @throws InterruptedException if interrupted while waiting for an element
     */
    public String getListObject() throws InterruptedException {
        return takeObject();
    }

    /**
     * Non-blocking offer: inserts if capacity allows; otherwise returns {@code false}
     * immediately without waiting.
     *
     * @param item value to insert
     * @return {@code true} if inserted, {@code false} if the queue was full
     */
    public boolean offerObject(String item) {
        return queue.offer(item);
    }

    /**
     * Timed offer: waits up to {@code timeout} for space, then gives up.
     *
     * @param item    value to insert
     * @param timeout how long to wait
     * @param unit    time unit for {@code timeout}
     * @return {@code true} if inserted within the timeout
     * @throws InterruptedException if interrupted while waiting
     */
    public boolean offerObject(String item, long timeout, TimeUnit unit) throws InterruptedException {
        return queue.offer(item, timeout, unit);
    }

    /**
     * Non-blocking poll: returns the head if present; otherwise {@code null}
     * without waiting.
     *
     * @return head element, or {@code null} if empty
     */
    public String pollObject() {
        return queue.poll();
    }

    /**
     * Current number of elements waiting in the queue.
     *
     * @return size of the underlying {@link ArrayBlockingQueue}
     */
    public int size() {
        return queue.size();
    }

    /**
     * Remaining capacity before the next {@link #putObject(String)} would block.
     *
     * @return free slots in the bounded queue
     */
    public int remainingCapacity() {
        return queue.remainingCapacity();
    }

    /**
     * Demo: one producer thread and one consumer thread sharing this queue.
     * The small capacity forces blocking so you can observe coordination.
     *
     * @param args unused
     * @throws InterruptedException if the main thread is interrupted while joining
     */
    public static void main(String[] args) throws InterruptedException {
        // Capacity 3: producer will block once three items are waiting unconsumed.
        BlockingQueue sharedQueue = BlockingQueue.create(3);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 8; i++) {
                    String item = "item-" + i;
                    System.out.println(Thread.currentThread().getName() + " producing " + item
                            + " (size before put=" + sharedQueue.size() + ")");
                    sharedQueue.putObject(item);
                    System.out.println(Thread.currentThread().getName() + " put " + item
                            + " (size after put=" + sharedQueue.size() + ")");
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "producer");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 8; i++) {
                    // Slightly slower consumer → queue fills and producer blocks on put.
                    Thread.sleep(250);
                    String item = sharedQueue.takeObject();
                    System.out.println(Thread.currentThread().getName() + " consumed " + item
                            + " (size after take=" + sharedQueue.size() + ")");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "consumer");

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println("Done. Final size=" + sharedQueue.size());
    }
}
