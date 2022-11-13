package com.soft.n_proxy.e;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * 为了解决d包中的问题,利用jdk的动态代理创建代理对象的代码太复杂! 我们要封装，以简化代码！
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

class MyHandler implements InvocationHandler {

    private Object target;

    public MyHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName() + "开始，参数是: " + Arrays.toString(args));
        // 调用真实对象的真实方法！
        Object r = method.invoke(target, args);// c为什么报错？
        System.out.println(method.getName() + "结束，结果是: " + r);
        return r;
    }
}

class MyProxy {
    //作者，在封装功能的时候，无法预测未来的用户，将会传入什么样的目标对象!
    //封装:对外隐藏复杂的实现细节，暴露出简单的使用方法!
    public static Object getProxy(Object target) {
        ClassLoader cl = Test.class.getClassLoader();
        Class[] interfaces = target.getClass().getInterfaces();
        Object proxy = Proxy.newProxyInstance(cl, interfaces, new MyHandler(target));
        return proxy;
    }
}
//============================================================

interface Vampire {
    void drinkblood();
}

class YoungVampire implements Vampire {

    @Override
    public void drinkblood() {
        System.out.println("我吸！");
    }
}

//============================================================

interface A {

    void f1();
    void f2();
}
class Foo implements A{

    public void f1(){

    }
    public void f2(){

    }
}

public class Test {
    public static void main(String[] args) {

        Vampire v = new YoungVampire();

        Vampire proxy = (Vampire)MyProxy.getProxy(v);

        proxy.drinkblood();

//        ICalc c = new CalcImpl();
//        ICalc proxy = (ICalc) MyProxy.getProxy(c);
//        proxy.add(1,2);

//        Foo f = new Foo();
//        A proxy1 = (A) new MyProxy().getProxy(f);
//        proxy1.f1();

//        System.out.println(new CalcImpl());
//        System.out.println(Arrays.toString(new CalcImpl().getClass().getInterfaces()));
//
//        System.out.println(new Foo());
//        System.out.println(Arrays.toString(new Foo().getClass().getInterfaces()));
    }
}
