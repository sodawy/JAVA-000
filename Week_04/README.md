# 并发
## 作业
### 作业0
    用尽可能多的方式在主线程生成新线程，执行fib30，并返回值
- 思路
1. 朴素`Thread(Runnable target)`。Runnable没有返回值，所以此类方法需要解决返回值问题
    - 1.1 `start()`后，子线程通过**自旋阻塞**，直到计算完成返回
    - 1.2 `start()`后，调用**`join()`**, 然后获取返回值
    - 1.3 主线程通过`park(t)`, 子线程完成计算后，`unpark` 主线程
2. 线程池。通过`Executors/ThreadPoolExecutor`获取`ExecutorService`，然后执行`submit()`
3. 互斥锁。`synchronized`
4. 信号量。`semaphore`
5. 条件锁。 `condition/lock`
6. `CyclicBarrier`
7. `CountDownLatch`
8. `Future`
9. `FutureTask`
10. `CompletableFuture`
 
    
 
### 作业1
    列举常用的并发操作 API 和工具类，简单分析其使用场景和优缺点。

### 作业2
    请思考：什么是并发？什么是高并发？实现高并发高可用系统需要考虑哪些因素，对于这些你是怎么理解的？
    

### 作业3
    请思考：还有哪些跟并发类似 / 有关的场景和问题，有哪些可以借鉴的解决办法。
    

### 作业4
    把多线程和并发相关知识带你梳理一遍，画一个脑图，截图上传到 Github 上。
    可选工具：xmind，百度脑图，wps，MindManage 或其他。
![jvm-args](https://github.com/sodawy/JAVA-000/blob/main/Week_04/JavaConcurrent.png)
    


