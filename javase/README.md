# 一、类加载机制

### 过程/周期

1. 加载

    通过读取jar/war/动态代理等方式，在内存Java堆中生成java.lang.class对象；

2. 验证、准备、解析

    验证class中代码安全性

    准备正式给class分配到JVM中内存空间（方法区/堆）

    解析引用（将常量池中的符号引用替换为直接引用）过程；

3. 初始化

    最终通过类加载器执行初始化。

4. 使用/卸载

### 类加载器

* 4个类加载器：启动类、扩展类、应用程序类加载器、自定义类加载器

    启动类加载器（Bootstrap）负责加载\lib或 -Xbootclasspath 指定的路径下、且被JVM认可的、且按文件名识别的类

    扩展（Extension）加载\lib\ext或 java.ext.dirs系统变量指定的类库

    Application 负责加载用户路径（classpath）上的类库。

* 双亲委派模型

JVM使用双亲委派模型或通过继承java.lang.ClassLoader实现自定义类加载器的进行类加载。双亲委派时指将加载请求委派给父类加载完成，从而保证类加载器最终得到同样一个Object对象。

![Image text](https://github.com/QinL233/JavaSE/blob/master/JVM/img/双亲委派.png)


# 二、内存结构

| name | desc |
| ------ | ------ |
| Java堆 | 存放对象、数组，是垃圾回收进行区域 |
| 方法区 | 存放类信息、编译后代码、运行时常量（1.8后存放在元空间），是堆的逻辑空间 |
| 虚拟机栈 | 描述方法执行过程的内存结构模型，在每个方法执行时创建一个栈帧，栈帧中存储局部变量、操作数、动态链接、方法出口 |
| 本地方法栈 | 与栈类似，存储native方法 |
| 线程计数器 | 执行字节码的行号指示器，无规定OOM |

![Image text](https://github.com/QinL233/JavaSE/blob/master/JVM/img/内存区.png)

![Image text](https://github.com/QinL233/JavaSE/blob/master/JVM/img/JDK内存.png)

# 三、垃圾回收与算法

### 垃圾标志

* 引用计数法

引用计数法（查询引用计数器）判断对象是否被引用，无法判断循环引用

* 可达性分析

JAVA主要采用，又称为GC Root，可达路径法可用于判断对象是否循环。

### 引用类型

* 强引用

主要在变量赋值时。处于可达状态，无法回收。

* 软引用

通过集合类SoftReference实现，该类下的变量根据内存状态回收。

* 弱引用

通过集合类WeakReference实现，GC一定回收。

* 虚引用

通过PhantomReference类并配合ReferenceQueue实现，用于追踪gc回收实例。

### 4种垃圾回收算法

1. 分代收集算法

    划分新生代（Eden区、From区、To、区），老年代，永久代（即运行时常量池，1.7存放在方法区中，1.8存放在元空间）

![Image text](https://github.com/QinL233/JavaSE/blob/master/JVM/img/堆.png)

2. 复制算法

    一般在新生代使用，每一次GC检查扫描新生代（3个区），并将存活的Eden/To复制到From区、或From区复制到To区、且年龄+1，年龄达到阀值复制到老年代。

3. 标记清理算法

    将标记后的区域清理，会造成内存分布碎片化

4. 标记整理算法 

    将标记后的区域清理后，向左或向右整理，实现整理。

# 垃圾回收器

主要是单线程、多线程、3种算法的排列

![Image text](https://github.com/QinL233/JavaSE/blob/master/JVM/img/垃圾回收器.png)

1. -XX:+SerialGC
    
    | 新生代 | 老年代 |
    | ------ | ------ |
    | Serial | Serial Old |
    | 单线程+复制  | 单线程+标记整理 |
    
![Image text](https://github.com/QinL233/JavaSE/blob/master/JVM/img/串行收集器.png)

2. -XX:+UseParallelGC

    | 新生代 | 老年代 |
    | ------ | ------ |
    | Parallel | Parallel Old |
    | 多线程+复制  | 多线程+标记整理 |

![Image text](https://github.com/QinL233/JavaSE/blob/master/JVM/img/并行收集器.png)

3. -XX:+UseConcMarkSweepGC

    | 新生代 | 老年代 | 老年代 |
    | ------ | ------ | ------ |
    | ParNew | Serial Old | CMS |
    | 类似Parallel，多线程+复制  | 多线程+标记清理 | 采用初始标记、并发标记、同步标记、并发清理 |

    > cms 可使用-XX:CMSFullGCsBeForeCompaction控制每次gc是否采用`标记整理`，默认为0，即每次都采用 

![Image text](https://github.com/QinL233/JavaSE/blob/master/JVM/img/CMS收集器.png)

4. -XX:+UseG1GC

    > Garbage First : 俗称垃圾优先，采用独立分区+分代/混合模式对回收内存进行回收价值评估，从而对有价值的垃圾进行回收
    
![Image text](https://github.com/QinL233/JavaSE/blob/master/JVM/img/G1收集器.png)

![Image text](https://github.com/QinL233/JavaSE/blob/master/JVM/img/G1内存模型.png)