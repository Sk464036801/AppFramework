package com.appframework.thread.pattern;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Eric on 8/21/14.
 */
public class TestMain {

    public static void main(String[] args) {
        TestQueue tq=new TestQueue();
        TestProduct tp=new TestProduct(tq);
        TestConsumer tc=new TestConsumer(tq);
        Thread t1=new Thread(tp);
        Thread t2=new Thread(tc);
        t1.start();
        t2.start();
    }
}

/**
 * 队列(使用了信号量，采用synchronized进行同步，采用lock进行同步会出错，或许是还不知道实现的方法)
 */
class TestQueue {

    public static Object signal=new Object();
    boolean bFull=false;
    private java.util.List thingsList=new ArrayList();
    private final ReentrantLock lock = new ReentrantLock(true);
    BlockingQueue q = new ArrayBlockingQueue(10);
    /**
     * 生产
     * @param thing
     * @throws Exception
     */
    public void product(String thing) throws Exception{
        synchronized(signal){
            if(!bFull){
                bFull=true;
                //产生一些东西，放到 thingsList 共享资源中
                System.out.println("product");
                System.out.println("仓库已满，正等待消费...");
                thingsList.add(thing);
                signal.notify(); //然后通知消费者
            }
            signal.wait(); // 然后自己进入signal待召队列

        }

    }

    /**
     * 消费
     * @return
     * @throws Exception
     */
    public String consumer()throws Exception{
        synchronized(signal){
            if(!bFull)  {
                signal.wait(); // 进入signal待召队列，等待生产者的通知
            }
            bFull=false;
            // 读取buf 共享资源里面的东西
            System.out.println("consume");
            System.out.println("仓库已空，正等待生产...");
            signal.notify(); // 然后通知生产者
        }
        String result="";
        if(thingsList.size()>0){
            result=thingsList.get(thingsList.size()-1).toString();
            thingsList.remove(thingsList.size()-1);
        }
        return result;
    }
}


class TestConsumer implements Runnable {

    TestQueue obj;

    public TestConsumer(TestQueue tq){
        this.obj=tq;
    }

    public void run() {
        try {
            for(int i=0;i<10;i++){
                obj.consumer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class TestProduct implements Runnable {

    TestQueue obj;

    public TestProduct(TestQueue tq){
        this.obj=tq;
    }

    public void run() {
        for(int i=0;i<10;i++){
            try {
                obj.product("test"+i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}