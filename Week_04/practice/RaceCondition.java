package practice;

public class RaceCondition {
    static class Acc {
        private   int sum = 0;

        synchronized void incr() {
            sum++;
        }

        public int getSum() {
            return sum;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int LOOP = 100000;
        Acc acc1 = new Acc();
        for (int i = 0; i < LOOP; i++) {
            acc1.incr();
        }
        System.out.println("single thread:" + acc1.getSum());

        Acc acc2 = new Acc();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < LOOP / 2; i++) {
                acc2.incr();
            }
        }, "acc2-1");
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < LOOP / 2; i++) {
                acc2.incr();
            }
        }, "acc2-2");
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(acc2.getSum());
    }
}
