package homework.solutions;

import homework.Fib;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierSolutionImpl implements HomeWorkSolution {
    CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
    Integer ret;

    @Override
    public Object call() throws Exception {
        new Thread(() -> {
            Fib fib = new Fib();
            ret = fib.sum();
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        cyclicBarrier.await();
        return ret;
    }
}
