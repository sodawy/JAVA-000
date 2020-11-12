package homework.solutions;

import homework.Fib;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionSolutionImpl implements HomeWorkSolution {
    Integer ret;

    Lock lock = new ReentrantLock();
    Condition con = lock.newCondition();

    @Override
    public Object call() throws Exception {
        new Thread(() -> {
            lock.lock();
            try {
                Fib fib = new Fib();
                ret = fib.sum();
                con.signalAll();
            } finally {
                lock.unlock();
            }
        }).start();

        lock.lock();
        try {
            while (ret == null) {
                con.await(3, TimeUnit.SECONDS);
            }
        } finally {
            lock.unlock();
        }

        return ret;
    }
}
