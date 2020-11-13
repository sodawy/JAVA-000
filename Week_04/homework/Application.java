package homework;

import homework.solutions.*;

public class Application {
    public static void main(String[] args) throws Exception {
        Proxy proxy = new Proxy();
        proxy.add(new SpinSolutionImpl());
        proxy.add(new JoinSolutionImpl());
        proxy.add(new WaitNotifySolutionImpl());
        proxy.add(new ConditionSolutionImpl());
        proxy.add(new FutureSolutionImpl());
        proxy.add(new SemaphoreSolutionImpl());
        proxy.add(new CountDownLatchSolutionImpl());
        proxy.add(new CyclicBarrierSolutionImpl());
        proxy.add(new ThreadPoolSolutionImpl());
        proxy.add(new CompletableFutureSolutionImpl());
        proxy.add(new ExecutorCompletionServiceSolutionImpl());
        proxy.add(new StampedLockSolutionImpl());

        proxy.runAll();
    }
}
