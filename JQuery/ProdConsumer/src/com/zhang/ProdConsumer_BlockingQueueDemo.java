package com.zhang;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author YONGHONG ZHANG
 * @date 2019-06-30-14:06
 */
class MyResource{
    private volatile boolean FLAG = true;
    private AtomicInteger atomicInteger = new AtomicInteger();

    BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void  myProd() throws Exception{
        String data = null;
        boolean retValue;
        while (FLAG){
           data = atomicInteger.incrementAndGet()+"";
            retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (retValue){
                System.out.println(Thread.currentThread().getName()+"\t插入队列"+data+"成功");
            }else{
                System.out.println(Thread.currentThread().getName()+"\t插入队列"+data+"失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName()+"\t大老板叫停，表示FLAG=false，生产结束");
    }
    public void  myConsumer() throws Exception{
        String result = null;
        boolean retValue;
        while (FLAG){
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (null == result || result.equalsIgnoreCase("")){
                FLAG = false;
                System.out.println(Thread.currentThread().getName()+"\t超过2秒钟没有取到蛋糕，消费退出");
                return;
            }
            System.out.println(Thread.currentThread().getName()+"\t消费队列消费"+result+"成功");
        }
    }
    public void stop() throws Exception{
        this.FLAG = false;
    }
}

public class ProdConsumer_BlockingQueueDemo {
    public static void main(String[] args) {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t生产线程启动");
            try {
                myResource.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }, "Prod").start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t消费线程启动");
            try {
                myResource.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }, "Consumer").start();
       try{
           TimeUnit.SECONDS.sleep(5);
       } catch (InterruptedException e){
           e.printStackTrace();
       }
        System.out.println("5秒钟时间到，老板叫停，main线程停止");
        try {
            myResource.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
