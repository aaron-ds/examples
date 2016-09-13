package jcip;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WaitNotifyTest {

    private volatile boolean someState;
    private volatile boolean someOtherState;




    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newCachedThreadPool();

        CyclicBarrier barrier = new CyclicBarrier(3);

        WaitNotifyTest t = new WaitNotifyTest();
        t.setSomeOtherState(false);

        executorService.execute(() -> {
            try {
                barrier.await();
                t.stateDependantMethod();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executorService.execute(() -> {
            try {
                barrier.await();
                t.otherStateDependantMethod();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        barrier.await();
        Thread.sleep(2000);
        t.m();
        Thread.sleep(2000);

        t.setSomeOtherState(true);
        t.m();

    }


    synchronized void stateDependantMethod() throws InterruptedException {
        while(!someState) {
            System.out.println("Blocking as stateDependantMethod can't procceed");
            wait();
            System.out.println("stateDependantMethod notified");
        }
        System.out.println("stateDependantMethod proceeding");
    }

    synchronized void otherStateDependantMethod() throws InterruptedException {
        while(!someOtherState) {
            System.out.println("Blocking as otherStateDependantMethod can't procceed");
            wait();
            System.out.println("otherStateDependantMethod notified");
        }
        System.out.println("otherStateDependantMethod proceeding");
    }

    synchronized void m() {
        System.out.println("m called - notifying waiting threads");
        notify();
    }

    void setSomeState(boolean b) {
        someState = b;
    }

    void setSomeOtherState(boolean b) {
        someOtherState = b;
    }


}


