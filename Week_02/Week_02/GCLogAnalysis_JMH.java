package Week_02;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * GC日志生成演示与解读
 */

@BenchmarkMode({Mode.Throughput}) //衡量吞吐量, 运行时间
@Warmup(iterations = 1)                             //编译器代码预热
@State(Scope.Benchmark)                             //每个实例共享进程
@Measurement(iterations = 3, time = 10, timeUnit = TimeUnit.SECONDS) //运行3轮，每轮3s
@Threads(4)
@Fork(value = 1)
public class GCLogAnalysis_JMH {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(GCLogAnalysis_JMH.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }

    GCLogAnalysis_JMH gcLogAnalysis;

    @Setup
    public synchronized void createInstance() {
        if (gcLogAnalysis == null) {
            System.out.println("setup");
            gcLogAnalysis = new GCLogAnalysis_JMH();
        }
    }

    @TearDown
    public synchronized void cleanInstance() {
        System.out.println("tearDown");
        gcLogAnalysis = null;
    }

    //============================================

    @Benchmark
    @Fork(jvmArgsAppend = "-XX:+UseSerialGC", jvmArgs = {"-Xmx16g", "-Xms16g"})
    public void test_SerialGC_16g() {
        gcLogAnalysis.generateAndMayPutInToArr();
    }

    @Benchmark
    @Fork(jvmArgsAppend = "-XX:+UseParallelGC", jvmArgs = {"-Xmx16g", "-Xms16g"})
    public void test_ParallGC_16g() {
        gcLogAnalysis.generateAndMayPutInToArr();
    }

    @Benchmark
    @Fork(jvmArgsAppend = "-XX:+UseConcMarkSweepGC", jvmArgs = {"-Xmx16g", "-Xms16g"})
    public void test_CMS_16g() {
        gcLogAnalysis.generateAndMayPutInToArr();
    }

    @Benchmark
    @Fork(jvmArgsAppend = "-XX:+UseG1GC", jvmArgs = {"-Xmx16g", "-Xms16g"})
    public void test_G1_16g() {
        gcLogAnalysis.generateAndMayPutInToArr();
    }

    //============================================

    @Benchmark
    @Fork(jvmArgsAppend = "-XX:+UseSerialGC", jvmArgs = {"-Xmx8g", "-Xms8g"})
    public void test_SerialGC_8g() {
        GCLogAnalysis_JMH gcLogAnalysis = new GCLogAnalysis_JMH();
        gcLogAnalysis.generateAndMayPutInToArr();
    }

    @Benchmark
    @Fork(jvmArgsAppend = "-XX:+UseParallelGC", jvmArgs = {"-Xmx8g", "-Xms8g"})
    public void test_ParallGC_8g() {
        GCLogAnalysis_JMH gcLogAnalysis = new GCLogAnalysis_JMH();
        gcLogAnalysis.generateAndMayPutInToArr();
    }

    @Benchmark
    @Fork(jvmArgsAppend = "-XX:+UseConcMarkSweepGC", jvmArgs = {"-Xmx8g", "-Xms8g"})
    public void test_CMS_8g() {
        GCLogAnalysis_JMH gcLogAnalysis = new GCLogAnalysis_JMH();
        gcLogAnalysis.generateAndMayPutInToArr();
    }

    @Benchmark
    @Fork(jvmArgsAppend = "-XX:+UseG1GC", jvmArgs = {"-Xmx8g", "-Xms8g"})
    public void test_G1_8g() {
        GCLogAnalysis_JMH gcLogAnalysis = new GCLogAnalysis_JMH();
        gcLogAnalysis.generateAndMayPutInToArr();
    }

    //============================================

    @Benchmark
    @Fork(jvmArgsAppend = "-XX:+UseSerialGC", jvmArgs = {"-Xmx2g", "-Xms2g"})
    public void test_SerialGC_2g() {
        GCLogAnalysis_JMH gcLogAnalysis = new GCLogAnalysis_JMH();
        gcLogAnalysis.generateAndMayPutInToArr();
    }

    @Benchmark
    @Fork(jvmArgsAppend = "-XX:+UseParallelGC", jvmArgs = {"-Xmx2g", "-Xms2g"})
    public void test_ParallGC_2g() {
        GCLogAnalysis_JMH gcLogAnalysis = new GCLogAnalysis_JMH();
        gcLogAnalysis.generateAndMayPutInToArr();
    }

    @Benchmark
    @Fork(jvmArgsAppend = "-XX:+UseConcMarkSweepGC", jvmArgs = {"-Xmx2g", "-Xms2g"})
    public void test_CMS_2g() {
        GCLogAnalysis_JMH gcLogAnalysis = new GCLogAnalysis_JMH();
        gcLogAnalysis.generateAndMayPutInToArr();
    }

    @Benchmark
    @Fork(jvmArgsAppend = "-XX:+UseG1GC", jvmArgs = {"-Xmx2g", "-Xms2g"})
    public void test_G1_2g() {
        GCLogAnalysis_JMH gcLogAnalysis = new GCLogAnalysis_JMH();
        gcLogAnalysis.generateAndMayPutInToArr();
    }

    final private int CACHE_SIZE = 2000;
    final private Random random;
    final private Object[] cacheGarbege;
    final private int LOOP_TIMES = 20000;

    public GCLogAnalysis_JMH() {
        random = new Random();
        cacheGarbege = new Object[2000];
    }

    /**
     * 调用生成对象，并有可能放入Object[] cacheGarbege
     */
    final private void generateAndMayPutInToArr() {
        // 生成垃圾对象
        for (int i = 0; i < LOOP_TIMES; i++) {
            Object garbage = generateGarbage(100 * 1024);
            int randomIndex = random.nextInt(2 * CACHE_SIZE);
            if (randomIndex < CACHE_SIZE) {
                cacheGarbege[randomIndex] = garbage;
            }
        }
    }

    /**
     * 生成对象
     *
     * @param maxSize
     * @return
     */
    final private Object generateGarbage(int maxSize) {
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