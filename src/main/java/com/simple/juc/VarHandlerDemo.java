package com.simple.juc;

import lombok.SneakyThrows;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * @author ZhuYX
 * @date 2021/04/13
 */
public class VarHandlerDemo {

    public static String name = "ab";


    /**
     * VarHandle 来使用 plain、opaque、release/acquire 和 volatile 四种共享内存的访问模式
     * opaque: Varhandle 中对 getOpaque 、 setOpaque 的说明是，按程序顺序执行,不确保其他线程可见顺序。
     * release/acquire: setRelease 确保前面的 load 和 store 不会被重排序到后面，但不确保后面的 load 和 store 重排序到前面；
     *                  getAcquire 确保后面的 load 和 store 不会被重排序到前面，但不确保前面的 load 和 store 被重排序。
     * volatile 确保程序执行顺序，能保证变量之间的不被重排序。
     *
     * 这四种共享内存的访问模式又分为写入访问模式、读取访问模式、原子更新访问模式、数值更新访问模式、按位原子更新访问模式。
     *    写入访问模式(write access modes) 获取指定内存排序效果下的变量值，包含的方法有 get、getVolatile、getAcquire、getOpaque 。
     *    读取访问模式(read access modes) 在指定的内存排序效果下设置变量的值，包含的方法有 set、setVolatile、setRelease、setOpaque 。
     *    原子更新模式(atomic update access modes)  原子更新访问模式，例如，在指定的内存排序效果下，原子地比较和设置变量的值，包含的方法有 compareAndSet、weakCompareAndSetPlain、weakCompareAndSet、weakCompareAndSetAcquire、weakCompareAndSetRelease、compareAndExchangeAcquire、compareAndExchange、compareAndExchangeRelease、getAndSet、getAndSetAcquire、getAndSetRelease 。
     *    数值更新访问模式(numeric atomic update access modes)    数字原子更新访问模式，例如，通过在指定的内存排序效果下添加变量的值，以原子方式获取和设置。 包含的方法有 getAndAdd、getAndAddAcquire、getAndAddRelease 。
     *    按位原子更新访问模式(bitwise atomic update access modes )    按位原子更新访问模式，例如，在指定的内存排序效果下，以原子方式获取和按位 OR 变量的值。 包含的方法有 getAndBitwiseOr、getAndBitwiseOrAcquire、getAndBitwiseOrRelease、 getAndBitwiseAnd、getAndBitwiseAndAcquire、getAndBitwiseAndRelease、getAndBitwiseXor、getAndBitwiseXorAcquire ， getAndBitwiseXorRelease 。
     *
     * 内存可见性：opaque（支持）， release/acquire（支持），volatile（支持）
     *
     * @param args
     */
    @SneakyThrows
    public static void main(String[] args) {

        var point = new Point();

        // System.out.println(xVarHandle.get(point));

        // xVarHandle.accessModeType(VarHandle.AccessMode.COMPARE_AND_EXCHANGE);

        // try {
        //     Object invoke = null;
        //     var xMethodHandler = MethodHandles.lookup().bind(point, "getX", MethodType.genericMethodType(0));
        //     invoke = xMethodHandler.invokeWithArguments();
        //     System.out.println(invoke);
        // } catch (Throwable throwable) {
        //     throwable.printStackTrace();
        // }

        // System.out.println(name);
        // nameStaticVarHandle.set("ddd");
        // System.out.println(name);

        // var thread = new Thread(() -> {
        //     var opaque = xVarHandle.getOpaque(point);
        //     if (opaque != null) {
        //         System.out.println("point x " + opaque);
        //     }
        // });
        // thread.start();
        // xVarHandle.setOpaque(point, 12L);


        // var acThread = new Thread(() -> {
        //     var opaque = xVarHandle.getAcquire(point);
        //     if (opaque != null) {
        //         System.out.println("point x " + opaque);
        //     }
        // });
        // acThread.start();
        // xVarHandle.setRelease(point, 12L);


        var acThread = new Thread(() -> {
            var opaque = xVarHandle.getVolatile(point);
            if (opaque != null) {
                System.out.println("point x " + opaque);
            }
        });
        acThread.start();
        xVarHandle.setVolatile(point, 12L);

    }


    public static final VarHandle xVarHandle;
    public static final VarHandle yVarHandle;
    public static final VarHandle nameStaticVarHandle;

    static {

        try {
            // 获取访问 protected、public 的 Lookup
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            // 获取访问私有变量的 Lookup
            MethodHandles.Lookup privateLookupIn = MethodHandles.privateLookupIn(Point.class, MethodHandles.lookup());

            // xVarHandle = lookup.findVarHandle(Point.class, "x", Long.class);
            // 创建对象中非静态字段的VarHandle
            xVarHandle = privateLookupIn.findVarHandle(Point.class, "x", Long.class);
            yVarHandle = privateLookupIn.findVarHandle(Point.class, "y", Long.class);

            // 创建对象中静态字段的VarHandle
            nameStaticVarHandle = lookup.findStaticVarHandle(VarHandlerDemo.class, "name", String.class);

            // privateLookupIn.unreflect()
            // 管理数组的 Varhandle
            var intArrVarHandler = MethodHandles.arrayElementVarHandle(int[].class);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
