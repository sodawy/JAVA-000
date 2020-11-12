package homework.solutions;

import homework.Fib;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FutureSolutionImpl implements HomeWorkSolution {
    @Override
    public Object call() throws Exception {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            Fib fib = new Fib();
            return fib.sum();
        });

        new Thread(futureTask).start();
        return futureTask.get();
    }
}
