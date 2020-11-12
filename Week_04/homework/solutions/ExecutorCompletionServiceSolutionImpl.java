package homework.solutions;

import homework.Fib;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorCompletionServiceSolutionImpl implements HomeWorkSolution {
    @Override
    public Object call() throws Exception {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        ExecutorCompletionService<Integer> executorCompletionService = new ExecutorCompletionService<>(singleThreadExecutor);

        executorCompletionService.submit(() -> {
            Fib fib = new Fib();
            return fib.sum();
        });


        return executorCompletionService.take().get();
    }
}
