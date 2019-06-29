package com.zhang;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author YONGHONG ZHANG
 * @date 2019-06-29-16:49
 * @description 超时控制
 */
public class BlockingQueueDemo4 {
    public static void main(String[] args) throws Exception {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("b", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("c", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("d", 2L, TimeUnit.SECONDS));
    }
}
