package Week_02;


import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * GC日志生成演示与解读
 */
public class GCLogAnalysis_Pause {

    private static Random random = new Random();

    public static void main(String[] args) {
        // 当前毫秒时间戳
        long startMillis = System.currentTimeMillis();
        // 持续运行毫秒数，可根据需要进行修改
        long timeoutMillis = TimeUnit.SECONDS.toMillis(3);
        // 结束时间戳
        long endMillis = startMillis + timeoutMillis;
        LongAdder counter = new LongAdder();
        System.out.println("正在执行");

        // 缓存一部分对象，进入老年代
        int cacheSize = 2000;
        Object[] cacheGarbege = new Object[cacheSize];

        // 在此时间范围内，持续循环
//        while (true) {
        while (System.currentTimeMillis() < endMillis) {
            // 生成垃圾对象
            Object garbage = generateGarbage(100 * 1024);
            counter.increment();
            int randomIndex = random.nextInt(2 * cacheSize);
            if (randomIndex < cacheSize) {
                cacheGarbege[randomIndex] = garbage;
            }
//            System.out.println("执行中！ 共生成对象次数：" + counter.longValue());
        }

        System.out.println("执行结束！ 共生成对象次数：" + counter.longValue());
    }

    /**
     * 生成对象
     *
     * @param maxSize
     * @return
     */
    private static Object generateGarbage(int maxSize) {
        int randomSize = random.nextInt(maxSize);
        int type = randomSize % 4;
        Object result = null;
        switch (type) {
            case 0:
                result = new int[randomSize];
                break;
            case 1:
                result = new byte[randomSize];
                break;
            case 2:
                result = new double[randomSize];
                break;
            default:
                StringBuilder builder = new StringBuilder();
                String randomString = "randomString-Anything";
                while (builder.length() < randomSize) {
                    builder.append(randomString);
                    builder.append(maxSize);
                    builder.append(randomSize);
                }
                result = builder.toString();
                break;
        }
        return result;
    }
}

//-XX:+UseSerialGC -Xms512m -Xmx1G -XX:+PrintGCDetails , max 50ms
//-XX:+UseParallelGC -Xms1G -Xmx1G -XX:+PrintGCDetails , max 40ms
//-XX:+UseConcMarkSweepGC -Xms1G -Xmx1G -XX:+PrintGCDetails , max 100ms
//-XX:+UseG1GC -Xms1G -Xmx1G -XX:+PrintGCDetails , max 40ms


//-XX:+UseSerialGC -Xms1G -Xmx1G -XX:+PrintGCDetails , max 50ms
//-XX:+UseParallelGC -Xms1G -Xmx1G -XX:+PrintGCDetails , max 40ms
//-XX:+UseConcMarkSweepGC -Xms1G -Xmx1G -XX:+PrintGCDetails , max 70ms
//-XX:+UseG1GC -Xms1G -Xmx1G -XX:+PrintGCDetails , max 10ms


//-XX:+UseSerialGC -Xms4G -Xmx4G -XX:+PrintGCDetails , max 120ms
//-XX:+UseParallelGC -Xms4G -Xmx4G -XX:+PrintGCDetails , max 120ms
//-XX:+UseConcMarkSweepGC -Xms4G -Xmx4G -XX:+PrintGCDetails , max 90ms
//-XX:+UseG1GC -Xms4G -Xmx4G -XX:+PrintGCDetails , max 120ms

//-XX:+UseSerialGC -Xms8G -Xmx8G -XX:+PrintGCDetails , max 160ms
//-XX:+UseParallelGC -Xms8G -Xmx8G -XX:+PrintGCDetails , max 140ms
//-XX:+UseConcMarkSweepGC -Xms8G -Xmx8G -XX:+PrintGCDetails , max 100ms
//-XX:+UseG1GC -Xms8G -Xmx8G -XX:+PrintGCDetails , max 80ms


//-XX:+UseG1GC -Xms8G -Xmx8G -XX:+PrintGCDetails -XXMaxGCPauseMillis=40, max 50ms


