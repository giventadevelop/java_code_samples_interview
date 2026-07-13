package com.boot2;

import java.util.ArrayList;
import java.util.List;

public class BlockingQueue {

    static BlockingQueue blockingQueue = null;

    private List<String> blockList = new ArrayList<>();

    private BlockingQueue() {
    }

    public static BlockingQueue getObject() {
        synchronized (BlockingQueue.class) {
            if (blockingQueue == null) {
                blockingQueue = new BlockingQueue();
            }
            return blockingQueue;
        }
    }

    public synchronized void putObject(String inObject) {
        blockList.add(inObject);
    }

    public synchronized String getListObject() {
        return blockList.get(0);
    }
}
