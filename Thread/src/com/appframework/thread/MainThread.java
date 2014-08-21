package com.appframework.thread;


/**
 * Created by Eric on 8/21/14.
 */
public class MainThread {

    public static void main(String[] args) {
        System.out.println("Main Thread Start ...");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                while (true) {
                    System.out.println("Sub 1-Max- Thread Start ..");

                    try {
                        Thread.sleep(1000*10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();

                    System.out.println(" --- groupName = " + threadGroup.getName());
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
                while (true) {

                    System.out.println("Sub 2-Normal- Thread Start ..");

                    try {
                        Thread.sleep(1000*10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

                while (true) {

                    System.out.println("Sub 3-Mix- Thread Start ..");
                    try {
                        Thread.sleep(1000*10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


        System.out.println("group name = " + Thread.currentThread().getThreadGroup().getName());
        System.out.println("Main Thread Stop ....");
    }
}
