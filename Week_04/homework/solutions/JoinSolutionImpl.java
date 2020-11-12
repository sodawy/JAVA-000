package homework.solutions;

import homework.Fib;

public class JoinSolutionImpl implements HomeWorkSolution {
    Integer ret;

    @Override
    public Object call() throws InterruptedException {
        Thread th = new Thread(() -> {
            Fib fib = new Fib();
            ret = fib.sum();
        });

        th.start();
        th.join();
        return ret;
    }
}
