package homework.solutions;

import homework.Fib;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class ThreadPoolSolutionImpl implements HomeWorkSolution {
    @Override
    public Object call() throws Exception {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        FutureTask<Integer> integerFutureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Fib fib = new Fib();
                return fib.sum();
            }
        });

        singleThreadExecutor.execute(integerFutureTask);

        Integer ret = integerFutureTask.get();
        singleThreadExecutor.shutdown();
        return ret;
    }
}
