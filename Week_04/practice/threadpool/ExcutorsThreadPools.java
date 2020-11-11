package practice.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExcutorsThreadPools {
    public static void main(String[] args) throws InterruptedException {
        final int cpuNum = Runtime.getRuntime().availableProcessors();

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(cpuNum);
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(cpuNum);

        for (ExecutorService es : new ExecutorService[]{cachedThreadPool, fixedThreadPool, singleThreadPool, scheduledThreadPool}) {
            System.out.println(runBy(es, 1000));
        }

    }

    private static long runBy(ExecutorService executorService, int taskCount) throws InterruptedException {
        long l = System.currentTimeMillis();

        for (int i = 0; i < taskCount; i++) {
            final int no = i;
            Runnable runnable = () -> {
                //System.out.println(Thread.currentThread().getName() + " [start]: " + no);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println(Thread.currentThread().getName() + "[end]: " + no);
            };

            executorService.execute(runnable);
        }

        executorService.shutdown();
        if (executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            return System.currentTimeMillis() - l;
        } else {
            return -1;
        }

    }
}
