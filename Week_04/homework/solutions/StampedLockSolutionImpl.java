package homework.solutions;

import homework.Fib;

import java.util.concurrent.locks.StampedLock;

public class StampedLockSolutionImpl implements HomeWorkSolution {
    Integer ret;
    StampedLock stampedLock = new StampedLock();

    @Override
    public Object call() throws Exception {
        new Thread(() -> {
            Fib fib = new Fib();

            long stamped = stampedLock.writeLock();
            try {
                ret = fib.sum();
            } finally {
                stampedLock.unlock(stamped);
            }

        }).start();

        while (true) {
            long opReadStamp = stampedLock.tryOptimisticRead();
            Integer opRet = ret;
            if (!stampedLock.validate(opReadStamp)) {
                long pesStamp = stampedLock.readLock();
                try {
                    Integer pesRet = ret;
                    if (pesRet != null) {
                        return pesRet;
                    }
                } finally {
                    stampedLock.unlockRead(pesStamp);
                }
            }
            if (opRet != null) {
                return ret;
            }
        }
    }
}
