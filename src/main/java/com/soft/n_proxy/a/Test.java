package com.soft.n_proxy.a;

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
public class Test {
    public static void main(String[] args) {
        ICalc c = new CalcImpl();
        System.out.println(c.add(1,1));
        System.out.println(c.sub(2,3));
        System.out.println(c.mul(4,7));
        System.out.println(c.div(10,4));
    }
}

/**
 * 变化:客户要求，为ICalc中的每一个方法，添加日志，记录方法开始，和结束的时机。
 */
