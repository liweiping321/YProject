package btrace;

import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.Threads.jstack;
import static com.sun.btrace.BTraceUtils.println;

/*方法上的注解
 @OnMethod 用来指定trace的目标类和方法以及具体位置, 被注解的方法在匹配的方法执行到指定的位置会被调用。"clazz"属性用来指定目标类名, 可以指定全限定类名, 比如"java.awt.Component", 也可以是正则表达式(表达式必须写在"//"中, 比如"/java\\.awt\\..+/")。"method"属性用来指定被trace的方法. 表达式可以参考自带的例子(NewComponent.java 和 Classload.java, 关于方法的注解可以参考MultiClass.java). 有时候被trace的类和方法可能也使用了注解. 用法参考自带例子WebServiceTracker.java. 针对注解也是可以使用正则表达式, 比如像这个"@/com\\.acme\\..+/ "，也可以通过指定超类来匹配多个类, 比如"+java.lang.Runnable"可以匹配所有实现了java.lang.Runnable接口的类. 具体参考自带例子SubtypeTracer.java。
 @OnTimer 定时触发Trace，时间可以指定，单位为毫秒，具体参考自带例子 Histogram.java。
 @OnError 当trace代码抛异常或者错误时，该注解的方法会被执行. 如果同一个trace脚本中其他方法抛异常, 该注解方法也会被执行。
 @OnExit 当trace方法调用内置exit(int)方法(用来结束整个trace程序)时, 该注解的方法会被执行. 参考自带例子ProbeExit.java。
 @OnEvent 用来截获"外部"btrace client触发的事件, 比如按Ctrl-C 中断btrace执行时，并且选择2，或者输入事件名称，将执行使用了该注解的方法, 该注解的value值为具体事件名称。具体参考例子HistoOnEvent.java
 @OnLowMemory 当内存超过某个设定值将触发该注解的方法, 具体参考MemAlerter.java
 @OnProbe 使用外部文件XML来定义trace方法以及具体的位置，具体参考示例SocketTracker1.java和java.net.socket.xml。
参数上的注解
 @Self 用来指定被trace方法的this，可参考例子AWTEventTracer.java 和 AllCalls1.java
 @Return 用来指定被trace方法的返回值，可参考例子Classload.java
 @ProbeClassName (since 1.1) 用来指定被trace的类名, 可参考例子AllMethods.java
 @ProbeMethodName (since 1.1) 用来指定被trace的方法名, 可参考例子WebServiceTracker.java。
 @TargetInstance (since 1.1) 用来指定被trace方法内部被调用到的实例, 可参考例子AllCalls2.java
 @TargetMethodOrField (since 1.1) 用来指定被trace方法内部被调用的方法名, 可参考例子AllCalls1.java 和 AllCalls2.java。
非注解的方法参数
 未使用注解的方法参数一般都是用来做方法签名匹配用的, 他们一般和被trace方法中参数出现的顺序一致. 不过他们也可以与注解方法交错使用, 如果一个参数类型声明为*AnyType[]*, 则表明它按顺序"通吃"方法所有参数. 未注解方法需要与*Location*结合使用:
 Kind.ENTRY-被trace方法参数
 Kind.RETURN- 被trace方法返回值
 Kind.THROW - 抛异常
 Kind.ARRAY_SET, Kind.ARRAY_GET - 数组索引
 Kind.CATCH - 捕获异常
 Kind.FIELD_SET - 属性值
 Kind.LINE - 行号
 Kind.NEW - 类名
 Kind.ERROR - 抛异常

属性上的注解
 @Export 该注解的静态属性主要用来与jvmstat计数器做关联. 使用该注解之后, btrace程序就可以向jvmstat客户端(可以用来统计jvm堆中的内存使用量)暴露trace程序的执行次数, 具体可参考例子ThreadCounter.java
 @Property 使用了该注解的trace脚本将作为MBean的一个属性, 一旦使用该注解, trace脚本就会创建一个MBean并向MBean服务器注册, 这样JMX客户端比如VisualVM, jconsole就可以看到这些BTrace MBean. 如果这些被注解的属性与被trace程序的属性关联, 那么就可以通过VisualVM 和jconsole来查看这些属性了. 具体可参考例子ThreadCounterBean.java 和 HistogramBean.java。
 @TLS 用来将一个脚本变量与一个ThreadLocal变量关联. 因为ThreadLocal变量是跟线程相关的, 一般用来检查在同一个线程调用中是否执行到了被trace的方法. 具体可参考例子OnThrow.java 和 WebServiceTracker.java
类上的注解
 @com.sun.btrace.annotations.DTrace 用来指定btrace脚本与内置在其脚本中的D语言脚本关联, 具体参考例子DTraceInline.java.
 @com.sun.btrace.annotations.DTraceRef 用来指定btrace脚本与另一个D语言脚本文件关联. 具体参考例子DTraceRefDemo.java.
 @com.sun.btrace.annotations.BTrace 用来指定该java类为一个btrace脚本文件.*/


/**
 由于Btrace会把脚本逻辑直接侵入到运行的代码中，所以在使用上做很多限制：
 1、不能创建对象
 2、不能使用数组
 3、不能抛出或捕获异常
 4、不能使用循环
 5、不能使用synchronized关键字
 6、属性和方法必须使用static修饰

 运行btrace脚本命令
 btrace [-I <include-path>] [-p <port>] [-cp <classpath>] <pid> <btrace-script> [<args>]
 示例:
 btrace -cp E:\gitProject\ 8836 Tracker.java
 参数含义:
 include-path指定头文件的路径，用于脚本预处理功能，可选；
 port指定BTrace agent的服务端监听端口号，用来监听clients，默认为2020，可选；
 classpath用来指定类加载路径，默认为当前路径，可选；
 pid表示进程号，可通过jps命令获取；
 btrace-script即为BTrace脚本；btrace脚本如果以.java结尾，会先编译再提交执行。可使用btracec命令对脚本进行预编译。
 args是BTrace脚本可选参数，在脚本中可通过"＄"和"＄length"获取参数信息。



 本测试案例使用的btrace版本是1.39，又不成功的可能跟btrace版本有关。

 结果输出到文件
 ./btrace -o mylog.txt $pid HelloWorld.java
 mylog会生成在应用的启动目录，而不是btrace的启动目录。其次，执行过一次-o之后，再执行btrace不加-o 也不会再输出回console，直到应用重启为止。

 所以有时也直接用转向了事：
 ./btrace $pid HelloWorld.java > mylog  转向输出的路径与-o不同，也不会出现-o的问题


 btrace/samples 里的是官方提供的例子
 */

/**
 * @author jianpeng.zhang
 * @since 2017/4/11.
 */
@BTrace
public class Tracker
{
    //@TLS 用来将一个脚本变量与一个ThreadLocal变量关联
    @TLS
    private static String name;

    // 匹配所有Filter类的子类
    // @OnMethod(clazz="+com.vip.demo.Filter", method="doFilter")

    // 匹配所有该注解的类和方法
    // @OnMethod(clazz="@javax.jws.WebService", method="@javax.jws.WebMethod")

    // @OnMethod(clazz = "/btrace.*/", method = "/.*/")
    // public static void onBar(@ProbeClassName String probeClass, @ProbeMethodName String probeMethod)
    // {
    //     println("entered onBar()");
    //     println(probeClass);    //btrace.MainTrace
    //     println(probeMethod);   //bar
    //     jstack();   // btrace.MainTrace.bar(MainTrace.java) btrace.MainTrace.main(MainTrace.java:14)
    // }

    //@Return只有当该方法有返回值时可用，不然会报错。
    @OnMethod(clazz = "/com.road.fire.fighting.PVPGame/", method = "onUpdateFighting", location = @Location(Kind.RETURN))
    public static void onUpdateFighting(@Self Object self, @ProbeMethodName String probeMethod, @Duration long duration)
    {
        if (duration / 1000000>40)
        {
            if (self != null)
                println(self);
            println(probeMethod);
            // println(returnData);
            println(duration / 1000000); //除以 1,000,000 才是毫秒
        }
    }

    //@Return只有当该方法有返回值时可用，不然会报错。
    @OnMethod(clazz = "/com.road.fire.fighting.BaseGame/", method = "update", location = @Location(Kind.RETURN))
    public static void gameUpdate(@Self Object self, @ProbeMethodName String probeMethod, @Duration long duration)
    {
        if (duration / 1000000>45)
        {
            if (self != null)
                println(self);
            println("BaseGame update--------------");
            println(probeMethod);
            // println(returnData);
            println(duration / 1000000); //除以 1,000,000 才是毫秒
        }
    }


    //@Return只有当该方法有返回值时可用，不然会报错。
    @OnMethod(clazz = "+com.road.fire.fighting.phy.living.Living", method = "update", location = @Location(Kind.RETURN))
    public static void update(@Self Object self,  @ProbeMethodName String probeMethod, @Duration long duration)
    {
        if (duration / 1000000>1)
        {
            if (self != null)
                println(self);
            println("Living update--------------");
            println(probeMethod);
            println(duration / 1000000); //除以 1,000,000 才是毫秒
            jstack();
        }
    }

    // 构造函数时调用
    // @OnMethod(clazz = "/btrace.*/", method = "<init>")
    // public static void init()
    // {
    //     println("init-----");
    // }

    //使用call一定要设置clazz和method，因为默认为空
    // @OnMethod(clazz = "/btrace.*/", method = "/.*/",
    //         location = @Location(value = Kind.CALL, clazz = "/.*/", method = "/.*/"))
    // public static void onCall(@ProbeMethodName String probeMethod)
    // {
    //     println(probeMethod);
    // }


    // 异常时调用
    // @OnMethod(clazz = "/btrace.*/", method = "/.*/", location = @Location(Kind.ERROR))
    // public static void onBind(Throwable exception, @Duration long duration)
    // {
    //
    // }

    // 运行到指定行
    // @OnMethod(clazz = "/btrace.*/", location = @Location(value = Kind.LINE, line = 23))
    // public static void online() {
    //     println(" reach line:23");
    // }

    // 使用了其他类，要加classpath
    // // 调用静态方法时，@Self里的对象为空。
    // @OnMethod(clazz="/btrace.*/", method="/.*/")
    // public static void m1(@Self MainTrace trace, @ProbeClassName String probeClass, @ProbeMethodName String probeMethod) { // all calls to the methods with signature "()"
    //     println(trace + "     " + "     " + probeMethod + "         " + probeClass);
    //     // println(trace + "");
    // }


    // 查看方法里调用了什么方法.不被执行的方法不会进入
    // @OnMethod(clazz = "/btrace.*/", method = "/.*/",
    //         location = @Location(value = Kind.CALL, clazz = "/.*/", method = "/.*/"))
    // public static void m(@Self Object self, @TargetMethodOrField String method,
    //         @ProbeMethodName String probeMethod)
    // { // all calls to the methods with signature "()"
    //     // TargetMethodOrField 好像是ProbeMethodName里调用的方法
    //     println(self + "     " + method  + "     " + probeMethod);
    // }

    // 筛选带String参数的方法
    // @OnMethod(clazz = "/btrace.*/", method = "/.*/",
    //         location = @Location(value = Kind.CALL, clazz = "/.*/", method = "/.*/"))
    // @Sampled(kind = Sampled.Sampler.Adaptive)
    // public static void callWithParam(@Self Object self, @TargetMethodOrField String method,
    //         @ProbeMethodName String probeMethod, String par)
    // { // all calls to the methods with signature "(String param)"
    //     println("=========start==========");
    //     println(par);
    //     println(self + "     " + method  + "     " + probeMethod);//Thread[Thread-152,5,main]     println     run
    //     println("=========end==========");
    // }

    // @OnMethod(clazz = "/btrace.*/", method = "/.*/",
    //         location = @Location(value = Kind.CALL, clazz = "/.*/", method = "/.*/"))
    // public static void n(@Self Object self, @ProbeClassName String pcm, @ProbeMethodName String pmn,
    //         @TargetInstance Object instance, @TargetMethodOrField String method, String text)
    // { // all calls to the methods with signature "(String)"
    //     println("Context: " + pcm + "#" + pmn + "#" + method + " " + instance + " " + text);
    //     //    Context: btrace.MainTrace#foo#println java.io.PrintStream@171ae4ae param406
    // }


    // //查看方法返回值
    // @OnMethod(clazz="/btrace.*/", method="/.*/",
    //         location=@Location(value=Kind.RETURN))
    // public static void o(@Self Object self, @ProbeMethodName String pmn, @Return AnyType args) { // all calls to methods
    // printArray(args);
    //     println(pmn + "       " + args);
    // }
    //
    // // 发送 name为L0的event时触发
    // @OnEvent("L0")
    // public static void setL0() {
    //     // BTraceUtils.setInstrumentationLevel(0);  设置level 可作用于 @OnMethod enableAt=@Level(">=1")
    //     println("event----------");
    // }



    // @OnMethod(clazz = "/btrace.*/", method = "foo")
    // public static void started()
    // {
    //     // btrace -cp E:\gitProject\实用小工具\out\production\实用小工具  4888 Tracker.java
    //     println(BTraceUtils.Sys.$length()); //传给脚本参数的数量 上面例子为2
    //     println(BTraceUtils.Sys.$(0));  // 4888
    //     println(BTraceUtils.Sys.$(1));  // Tracker.java
    // }

    // //定时调用
    // @OnTimer(4000)
    // public static void print() {
    //     println("onTime-------");
    // }


    //获得类的变量
    // @OnMethod(clazz = "/btrace.*/", method = "/bar/")
    // public static void onfinalize(@Self MainTrace me)
    // {
    //     Field fdField = field("btrace.MainTrace", "i");
    //     printFields(me);
    //     printFields(get(fdField, me));
    //     println("==========");
    // }

    //内存低于这个值是触发
    // @OnLowMemory(pool = "Tenured Gen", threshold = 600000000)
    // public static void onLowMem(MemoryUsage mu)
    // {
    //     println("onLowMem");
    //     println(mu);
    // }


    // //新建数组时调用，clazz为数组类型
    // @OnMethod(
    //         clazz = "/btrace.*/", // tracking in all classes; can be restricted to specific user classes
    //         method = "/.*/", // tracking in all methods; can be restricted to specific user methods
    //         location = @Location(value = Kind.NEWARRAY, clazz = "int"))
    // public static void onnew(@ProbeClassName String pcn, @ProbeMethodName String pmn, String arrType, int dim)
    // {
    //     println(pcn + "     " + pmn);
    //     println(arrType + "   " + dim); //arrType 对应clazz dim为维度
    // }



    // @Property
    // public static Profiler swingProfiler = BTraceUtils.Profiling.newProfiler();
    //
    // @OnMethod(clazz="/btrace.*/", method="/.*/")
    // public static void entry(@ProbeMethodName(fqn=true) String probeMethod) {
    //     BTraceUtils.Profiling.recordEntry(swingProfiler, probeMethod);
    // }
    //
    // @OnMethod(clazz="/btrace.*/", method="/.*/", location=@Location(value=Kind.RETURN))
    // public static void exit(@ProbeMethodName(fqn=true) String probeMethod, @Duration long duration) {
    //     BTraceUtils.Profiling.recordExit(swingProfiler, probeMethod, duration);
    // }
    //
    // @OnTimer(5000)
    // public static void timer() {
    //     BTraceUtils.Profiling.printSnapshot("Swing performance profile", swingProfiler);
    // }



    // @OnMethod(clazz="java.lang.System", method="getProperty")
    // public static void onGetProperty(String name) {
    //     println(name);      //name为getProperty的参数
    //     // call property safely here.
    //     println(BTraceUtils.Sys.Env.property(name));    //调用 System.getProperty(name);
    // }

    // //数组赋值时触发
    // @OnMethod(clazz="/btrace.*/", method="/.*/", location = @Location(Kind.ARRAY_SET))
    // public static void onArraySet(@ProbeMethodName String method, @ProbeClassName String className) {
    //     println("onArray set");
    //     println(className + "   " + method);
    // }

    // //数组取值时触发
    // @OnMethod(clazz="/btrace.*/", method="/.*/", location = @Location(Kind.ARRAY_GET))
    // public static void onArraySet(@ProbeMethodName String method, @ProbeClassName String className) {
    //     println("onArray get");
    //     println(className + "   " + method);
    // }

    // //变量被引用时触发 clazz 和 field 都需要设置
    // @OnMethod(clazz="/btrace.*/", method="/.*/",
    //         location = @Location(value=Kind.FIELD_GET, clazz="/.*/", field="j"))
    // public static void onFieldGet(@ProbeMethodName String method, @ProbeClassName String className) {
    //     println("on field get");
    //     println(className + "   " + method);
    // }

    // //变量被赋值时触发 clazz 和 field 都需要设置
    // @OnMethod(clazz="/btrace.*/", method="/.*/",
    //         location = @Location(value=Kind.FIELD_GET, clazz="/.*/", field="j"))
    // public static void onFieldSet(@ProbeMethodName String method, @ProbeClassName String className) {
    //     println("on field set");
    //     println(className + "   " + method);
    // }

}
