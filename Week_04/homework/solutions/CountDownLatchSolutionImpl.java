package homework.solutions;

import homework.Fib;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchSolutionImpl implements HomeWorkSolution {
    CountDownLatch cdl = new CountDownLatch(1);
    Integer ret;

    @Override
    public Object call() throws Exception {
        new Thread(() -> {
            Fib fib = new Fib();
            ret = fib.sum();

            cdl.countDown();
        }).start();

        cdl.await();

        return ret;
    }
}
