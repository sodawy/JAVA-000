package homework.solutions;

import homework.Fib;

import java.util.concurrent.Semaphore;

public class SemaphoreSolutionImpl implements HomeWorkSolution {
    Integer ret;
    Semaphore semaphore = new Semaphore(1);

    @Override
    public Object call() throws Exception {
        new Thread(() -> {
            try {
                semaphore.acquire();
                Fib fib = new Fib();
                ret = fib.sum();
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        semaphore.acquire();
        while (ret == null) {
            semaphore.release();
        }

        return ret;
    }
}
