package com.soft.n_proxy.d;

import java.lang.reflect.Proxy;

/**
 * 为了解决c包中的问题，我们必须学习一个jdk中的api：动态代理
 */
interface ICalc {
    int add(int a, int b);
    int sub(int a, int b);
    int mul(int a, int b);
    int div(int a, int b);
}

class CalcImpl implements ICalc {

    @Override
    public int add(int a, int b) {
        int r = a + b;
        return r;
    }

    @Override
    public int sub(int a, int b) {
        int r = a - b;
        return r;
    }

    @Override
    public int mul(int a, int b) {
        int r = a * b;
        return r;
    }

    @Override
    public int div(int a, int b) {
        int r = a / b;
        return r;
    }
}

//===================================================================================

public class Test {
    public static void main(String[] args) {
        /*
         * //创建一个代理对象的。创建这个代理对象的时候，需要传入个3参数! !
         *
         * 第一个参数:类加载器
         * 地球人都知道，要实例化一个对象，就必须调用类的构造器!在构造器调用之前，jvm会加载该类的字节码!
         * jvm恰恰就是使用“类加载器”来加载类的字节码的!这-步是jvm自动完成的!
         * 以下代码，是动态创建一个代理对象的代码，可以说是一种不太正常的创建对象的方式，就算它不正常,它毕竟
         * 也是要创建对象的嘛!但凡创建对象，势必要先加载字节码，势必就要使用到类加载器，和构造器实例化不同的是:
         * 使用构造器实例化对象时，jvm会自动找到类加载器，而以下代码, 必须我们手动传入类加载器!
         */
        ClassLoader cl = Test.class.getClassLoader();

        /*
            第二个人参数:字节码数组
            地球人都知道，要创建一个类的对象，必须要先加载类的字节码,那么动态代理也不例外，所以我们才传入第一个参数:类加载器!
            问题是:这个传入的类加载器，加载的是哪个类的字节码? ?

            对比，使用构造器创建对象时，加载的字节码很明确:
            new String();       jvm就会加载String.class
            new Date() ;        jvm就会加载Date.class
            new ArrayList();    jvm就会加载ArrayList.class

            而使用动态代理api创建对象时，加载哪个字节码呢?
         */

//        Proxy.newProxyInstance(cl);
    }
}

/**
 * 目前，这个写法，符合了开闭原则，毕竟我们没有修改作者的代码
 *
 *
 * 这样写是有很大的缺点的:
 * 1.工作量太大,我们要为每个方法添加日志功能!
 * // 2.如果ICalc和CalcImpl不是我们自己创建的，是被发现的，手头是没有源代码的!我们不能直接修改源代码!
 * 3.需求如果再次变化呢?比如,客户要求我们使用英文写日志!过了一段时间，还是中文好! !
 */
