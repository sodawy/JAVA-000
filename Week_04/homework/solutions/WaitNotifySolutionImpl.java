package homework.solutions;

import homework.Fib;

public class WaitNotifySolutionImpl implements HomeWorkSolution {

    Integer ret;
    Object lock = new Object();

    @Override
    public Object call() throws InterruptedException {
        new Thread(() -> {
            Fib fib = new Fib();
            ret = fib.sum();

            synchronized (lock) {
                lock.notify();
            }
        }).start();

        synchronized (lock) {
            lock.wait();
        }
        return ret;
    }
}
