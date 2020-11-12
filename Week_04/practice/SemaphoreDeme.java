package practice;

import java.util.concurrent.Semaphore;

public class SemaphoreDeme {

    public static void main(String[] args) {
        int N = 8;
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < N; i++) {
            new Worker(i, semaphore).start();
        }

    }

    static class Worker extends Thread {
        int num;
        Semaphore semaphore;

        Worker(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                this.semaphore.acquire();
                System.out.println("work: " + this.num + " got semaphore");
                Thread.sleep(3000);
                System.out.println("work: " + this.num + " drop semaphore");
                this.semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
