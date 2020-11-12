package homework.solutions;

import homework.Fib;

public class SpinSolutionImpl implements HomeWorkSolution {

    Integer ret;

    @Override
    public Object call() throws InterruptedException {
        new Thread(() -> {
            Fib fib = new Fib();
            ret = fib.sum();
        }).start();

        while (true) {
            Thread.sleep(1);
            if (ret != null) {
                return ret;
            }
        }

    }
}
