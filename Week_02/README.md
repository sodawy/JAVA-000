# 作业

## 作业一
### [吞吐][cpu密集]分析各GC不同内存下的GCLogAnalysis运行情况
- 环境参数
    - os配置：maxos 8core 32Gmem
    - vm参数：```-XX:+UseG1GC -Xms512m -Xmx512m -verbose:gc```
    - jdk版本: jdk1.8.0_261
    - class src: Week_02.GCLogAnalysis_JMH
- 使用jhm运行benchmark结果
    - 实验目的：测试不同内存&GC的效率
    - 实验方式：jhm测试吞吐(固定时间内完成生成对象次数)多少判定效率，用例偏计算密集型
    - 实验结果：        
        ```
        Benchmark                                 Mode  Cnt  Score   Error  Units
        
        Week_02.GCLogAnalysis_JMH.test_SerialGC_2g   thrpt    3  0.639 ± 0.336  ops/s
        Week_02.GCLogAnalysis_JMH.test_ParallGC_2g   thrpt    3  0.317 ± 0.312  ops/s
        Week_02.GCLogAnalysis_JMH.test_CMS_2g        thrpt    3  0.690 ± 0.593  ops/s
        Week_02.GCLogAnalysis_JMH.test_G1_2g         thrpt    3  0.673 ± 0.433  ops/s
        
        Week_02.GCLogAnalysis_JMH.test_SerialGC_8g   thrpt    3  2.044 ± 0.299  ops/s
        Week_02.GCLogAnalysis_JMH.test_ParallGC_8g   thrpt    3  1.867 ± 0.464  ops/s
        Week_02.GCLogAnalysis_JMH.test_CMS_8g        thrpt    3  2.400 ± 0.192  ops/s
        Week_02.GCLogAnalysis_JMH.test_G1_8g         thrpt    3  2.538 ± 0.590  ops/s
        
        Week_02.GCLogAnalysis_JMH.test_SerialGC_16g  thrpt    3  3.527 ± 0.738  ops/s
        Week_02.GCLogAnalysis_JMH.test_ParallGC_16g  thrpt    3  4.059 ± 0.525  ops/s
        Week_02.GCLogAnalysis_JMH.test_CMS_16g       thrpt    3  2.269 ± 5.835  ops/s
        Week_02.GCLogAnalysis_JMH.test_G1_16g        thrpt    3  4.435 ± 0.205  ops/s
        ```
    - 实验分析
        1. 本实验参数跑512m时，全部gc方式均oom，说明对象生成速度绝对值大于gc回收出内存的绝对值
        2. 如果内存较小时，更频繁触发阈值，gc会比较频繁。
        3. 因为GC会导致分代区的全部处理，因此会出现效率随内存的增加，先增后降的效果；
            - 开始阶段，内存增加，吞吐提高：是因为gc次数少了，那么单次gc处理量多了，效率就更高了
            - 内存超过4G后，串行、并行、CMS随内存上升表现不如G1了。因为分代区域变大了，单次处理负担增加了
            - 串行、并行、CMS这三款GC，因为分代区域设计，单次gc效率存在一个临界值，是内存大小和单次gc效率高低的临界关系
            - 内存大于8G后，G1的region设计(每次处理的内存范围有限，region相对较小，不随堆的增加大幅增加)，及根据统计信息选择收益最高的region处理
        4. 因为测试程序为计算密集型，对cms不例，如果换成常规应用，存在io等待，那cms的并发gc线程就能理由io等待更好的利用cpu
    - 实验结论
        - 内存的大小会影响GC次数，从而因为GC运行方式不同，影响GC效率
        - 大内存(>8G)时，计算密集型，G1吞吐效率最高; CMS效率最差 
        - 小内存(<2G)时，并行GC吞吐效率最差；其他三者差不多
        
### [最大停顿][cpu密集]分析各GC不同内存下的GCLogAnalysis运行情况
- 环境参数
    - os配置：maxos 8core 32Gmem
    - vm参数：```-XX:+UseG1GC -Xms1G -Xmx1G -XX:+PrintGCDetails```
    - jdk版本: jdk1.8.0_261
    - class src: Week_02.GCLogAnalysis_Pause
- 使用jhm运行benchmark结果
    - 实验目的：测试不同内存&GC的效率
    - 实验方式：jhm测试**最大停顿**(gclog中最大的`real=${max_pause_ms}`)多少判定效率，用例偏计算密集型
    - 实验结果：        
        ```
        Benchmark           max_pause_ms
        SerialGC_512m       50
        ParallGC_512m       40
        CMS_512m            100
        G1_512m             40        
      
        SerialGC_1g         50
        ParallGC_1g         40
        CMS_1g              70
        G1_1g               10
        
        SerialGC_4g         120
        ParallGC_4g         120
        CMS_4g              90
        G1_4g               120
        
        SerialGC_8g         160
        ParallGC_8g         140
        CMS_8g              100 
        G1_8g               80 
        ```
    - 实验分析&结论
        1. 串行gc始终停顿很高
        2. 并行gc随内存增加，停顿略低于串行gc，但仍比cms和g1高
        3. g1在未设置`-XXMaxGCPauseMillis`参数时，随内存增加，表现优于其他gc
        4. g1在设置`-XXMaxGCPauseMillis`情况下，停顿时间基本可控在参数范围内，极少情况会略高于参数
        
# 作业二
### [吞吐][gc停顿][io密集]分析各GC不同内存下的gateway响应请求运行情况
- 环境参数
    - os配置：maxos 8core 32Gmem
    - vm参数：```-XX:+UseSerialGC -Xms128m -Xmx128m -Xloggc:gc.log -XX:+PrintGCDetails```
    - jdk版本: jdk1.8.0_261
    - class src: Week_02.gateway-server-0.0.1.jar
- 实验
    - 实验目的：测试io密集情况下，不同内存，不同gc的吞吐和gc停顿，分析学习各gc
    - 实验方式
        - 启动gateway后，wrk测试`wrk -t20 -c60 -d60s http://localhost:8088/api/hello`
        - 结果收集方式 
            - gc停顿的avg和max来自gclog上传GCEasy后的分析，其实通过real也能得出
            - 吞吐来自wrk的结果`requests`数
        - 本次实验未取多次平均值，会存在环境误差
    - 实验结果
        - **avg_pause_ms**
        
        | GC/MEM    | 128M | 512M    | 2G      | 8G      |
        | --------- | ---- | ------- | ------- | ------- |
        | Serial    | 1.92 | 2.69 | 3.92 | 10.0       |
        | Parallel  | 1.58 | 1.71 | 2.72 | 5 |
        | CMS       | 0.92 | 3.25 | 5.25 | 6.09 |
        | G1        | 1.89 | 1.86 | 5.08 | 12.3 |
        
        - **max_pause_ms**
                
        | GC/MEM    | 128M | 512M    | 2G      | 8G      |
        | --------- | ---- | ------- | ------- |-------  |
        | Serial    | 50   | 70      | 110     | 110     |
        | Parallel  | 100  | 50      | 30      | 30       |
        | CMS       | 10   | 20      | 40      | 110     |
        | G1        | 10   | 20      | 20      | 20      |
        
        - **request**
                        
        | GC/MEM    | 128M    | 512M    | 2G      | 8G      |
        | --------- | ------- | ------- | ------- |------- |
        | Serial    | 2497106 | 2475255 | 2677218 | 2536251 |
        | Parallel  | 2470752 | 2670610 | 2604555 | 2708634 |
        | CMS       | 2291128 | 2574799 | 2471351 | 2811991 |
        | G1        | 2316596 | 2604045 | 2430644 | 2634190 |
        
    - 分析结论
        - 果然io密集的结果和cpu密集的结果不太相同
        - `串行和并行gc`无论`平均停顿`还是`最大gc停顿`，都差与`cms`和`G1`
        - 非大内存情况下，`串行和并行`gc`平均停顿`低，但`最大停顿`高，而`cms`和`G1`则是`平均停顿`高，`最大停顿`低；反映到吞吐上也逻辑一致，前二者吞吐高
        - 大内存情况下，`最大停顿`差距则更加明显，如果对`最大停顿`有要求，`G1`是最好的选择
        - 比较意外的是8g内存下，`并行gc`完胜`cms`。所以除非没法用`G1`，又要求低`最大延迟`，否则不用`cms`
        
# 作业- httpClient发请求
    - 见代码RequestByHttpClient.java
       