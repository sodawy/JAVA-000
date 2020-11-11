package practice;

public class ProductConsumeWaitNotify {
    final private int MAX = 20;
    int cur;

    synchronized public void product() throws InterruptedException {
        while (true) {
            if (cur == MAX) {
                System.out.println("库存已满，不再生产");
                wait();
            } else {
                cur++;
                System.out.println(cur);
                notify();
                wait();
            }
            notify();
        }
    }

    synchronized public void consume() throws InterruptedException {
        while (true) {
            if (cur == 0) {
                System.out.println("库存已空，不在消费");
                wait();
            } else {
                cur--;
                System.out.println(cur);
                notify();
                wait();
            }

        }
    }

    public static void main(String[] args) {
        ProductConsumeWaitNotify productConsumeWaitNotify = new ProductConsumeWaitNotify();
        new Thread(() -> {
            try {
                productConsumeWaitNotify.product();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                productConsumeWaitNotify.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


}
