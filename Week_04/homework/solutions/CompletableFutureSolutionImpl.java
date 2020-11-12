package homework.solutions;

import homework.Fib;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureSolutionImpl implements HomeWorkSolution {
    @Override
    public Object call() throws Exception {
        return CompletableFuture.supplyAsync(() -> {
            Fib fib = new Fib();
            return fib.sum();
        }).join();
    }
}
