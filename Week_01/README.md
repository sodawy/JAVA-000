# 作业
# 第一题 分析字节码文件
    # 1.关于本地变量名: 如果编译时java -g 在Constant pool中就会包含本地变量名abc，对调试更友好
    # 2.根据指令逐行注释见下
    
    ====分割线，以下为javap的内容==================================================================================
    
    Classfile /Users/soda/learn/JAVA-000/Week_01/Hello.class
      Last modified Oct 16, 2020; size 516 bytes
      MD5 checksum bcdb5e713136dd4453c80cc57efe9e57
      Compiled from "Hello.java"
    class Hello
      minor version: 0
      major version: 55                       //jdk版本号，11
      flags: (0x0020) ACC_SUPER
      this_class: #2                          // Hello
      super_class: #3                         // java/lang/Object
      interfaces: 0, fields: 0, methods: 2, attributes: 1
    Constant pool:
       #1 = Methodref          #3.#24         // java/lang/Object."<init>":()V
       #2 = Class              #25            // Hello
       #3 = Class              #26            // java/lang/Object
       #4 = Utf8               <init>
       #5 = Utf8               ()V
       #6 = Utf8               Code
       #7 = Utf8               LineNumberTable
       #8 = Utf8               LocalVariableTable
       #9 = Utf8               this
      #10 = Utf8               LHello;
      #11 = Utf8               main
      #12 = Utf8               ([Ljava/lang/String;)V
      #13 = Utf8               i
      #14 = Utf8               I
      #15 = Utf8               args
      #16 = Utf8               [Ljava/lang/String;
      #17 = Utf8               a
      #18 = Utf8               b
      #19 = Utf8               c
      #20 = Utf8               StackMapTable
      #21 = Class              #16            // "[Ljava/lang/String;"
      #22 = Utf8               SourceFile
      #23 = Utf8               Hello.java
      #24 = NameAndType        #4:#5          // "<init>":()V
      #25 = Utf8               Hello
      #26 = Utf8               java/lang/Object
    {
      Hello();                          //因为未重写任何构造函数，编译器自动添加了无参空构造方法
        descriptor: ()V
        flags: (0x0000)
        Code:
          stack=1, locals=1, args_size=1
             0: aload_0
             1: invokespecial #1                  // Method java/lang/Object."<init>":()V
             4: return
          LineNumberTable:                        //源码行号和字节码行号映射
            line 2: 0
          LocalVariableTable:                     //本地变量表
            Start  Length  Slot  Name   Signature
                0       5     0  this   LHello;
    
      public static void main(java.lang.String[]);
        descriptor: ([Ljava/lang/String;)V      //方法描述符，(形参列--对象数组)空返回值
        flags: (0x0009) ACC_PUBLIC, ACC_STATIC  //访问控制，对应public static
        Code:                                   //代码
          stack=2, locals=5, args_size=1        //栈最大深度2，本地变量表最大槽数5，形参数1(静态方法没this)
             0: iconst_1                //常量数字1进栈
             1: istore_1                //常量数字1出栈，入LocalVariableTable，位置Slot1, 至此完成 int a = 1
             2: iload_1                 //常量数字1进栈
             3: bipush        10        //常量数字10进栈，只有1-5有直接指令，后面的有一个指令+一个操作数
             5: iadd                    //将栈中两个数字相加
             6: istore_2                //将数字结果保存至本地变量表，位置Slot2，至此完成 int b = a + 10
             7: iconst_0                //常量数字0进栈
             8: istore_3                //常量数字0出栈，入LocalVariableTable，位置Slot3，至此完成 int c = 0;
             9: iconst_0                //常量数字0进栈
            10: istore        4         //弹出栈顶，如本地变量槽4
            12: iload         4         //本地变量槽4入栈
            14: bipush        10        //入栈数字10
            16: if_icmpge     30        //比较栈中数字，如果比较结果false，跳入指令30，即return
            19: iload_3                 //如果比较结果true，本地变量槽3入栈
            20: iload         4         //本地变量槽4入栈
            22: iadd                    //槽内int数字相加
            23: istore_3                //计算结果出栈如本地变量槽3
            24: iinc          4, 1      //槽4变量+1
            27: goto          12        //跳转至指令12
            30: return                  //方法退出
          LineNumberTable:              //源码行号和字节码行号映射
            line 4: 0                   //源码第4行，对应字节码Code中的0(0: iconst_1)
            line 5: 2
            line 6: 7
            line 7: 9
            line 8: 19
            line 7: 24
            line 10: 30
          LocalVariableTable:           //本地变量表
            Start  Length  Slot  Name   Signature
               12      18     4     i   I
                0      31     0  args   [Ljava/lang/String;
                2      29     1     a   I
                7      24     2     b   I
                9      22     3     c   I
          StackMapTable: number_of_entries = 2  //jvm在类加载的验证阶段，被类型检查器使用
            frame_type = 255 /* full_frame */
              offset_delta = 12
              locals = [ class "[Ljava/lang/String;", int, int, int, int ]
              stack = []
            frame_type = 250 /* chop */
              offset_delta = 17
    }
    SourceFile: "Hello.java"

# 第二题 带偏移的类加载
    import java.io.File;
    import java.io.IOException;
    import java.lang.reflect.InvocationTargetException;
    import java.lang.reflect.Method;
    import java.nio.file.Files;
    import java.nio.file.Paths;
    
    class ClassLoaderWithOffset extends ClassLoader {
        final int OFFSET = 255;
    
        /**
         * define class from file, specially, before calling ClassLoader::defineClass, change bytes by OFFSET
         *
         * @param name
         * @param filePath
         * @return
         * @throws IOException
         */
        public Class defineClassWithOffset(String name, String filePath) throws IOException {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            int len = bytes.length;
            for (int i = 0; i < len; i++) {
                bytes[i] = (byte) ((byte) OFFSET - bytes[i]);
            }
    
            return this.defineClass(name, bytes, 0, len);
        }
    
        public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
            //xlass file path
            String filePath = System.getProperty("user.dir") + File.separator + "Week_01" + File.separator + "Hello.xlass";
    
            //custom define classLoader with byte offset
            ClassLoaderWithOffset classLoaderWithOffset = new ClassLoaderWithOffset();
            Class helloClass = classLoaderWithOffset.defineClassWithOffset("Hello", filePath);
    
            //get a instance of hello,xlass and call method hello
            Method helloMethod = helloClass.getDeclaredMethod("hello");
            helloMethod.invoke(helloClass.newInstance());
        }
    }
# 第三题 内存区域加参数
![homework3](https://github.com/sodawy/JAVA-000/blob/main/Week_01/gc-mem.png)
# 笔记
# 字节码
![classfile](https://github.com/sodawy/JAVA-000/blob/main/Week_01/jvm-note-classfile.png)
# 类加载
![classloader](https://github.com/sodawy/JAVA-000/blob/main/Week_01/jvm-note-classloader.png)
# 内存模型
![jvm-mem](https://github.com/sodawy/JAVA-000/blob/main/Week_01/jvm-note-mem.png)
# jvm启动参数
![jvm-args](https://github.com/sodawy/JAVA-000/blob/main/Week_01/jvm-note-jvm-args.png)