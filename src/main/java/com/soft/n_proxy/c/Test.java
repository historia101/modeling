package com.soft.n_proxy.c;

/**
 * 为了解决b包中的问题，我们修改代码如下
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

class MyCalcImpl extends CalcImpl {
    @Override
    public int add(int a, int b) {
        //super代表父类特征，表示掉父类的add方法
        System.out.println("add方法开始，参数是：" + a + "," + b);
        int r = super.add(a, b);
        System.out.println("add方法结束，结果是：" + r);
        return r;
    }

    @Override
    public int sub(int a, int b) {
        System.out.println("sub方法开始，参数是：" + a + "," + b);
        int r = super.sub(a, b);
        System.out.println("sub方法结束，结果是：" + r);
        return r;
    }

    @Override
    public int mul(int a, int b) {
        System.out.println("mul方法开始，参数是：" + a + "," + b);
        int r = super.mul(a, b);
        System.out.println("mul方法结束，结果是：" + r);
        return r;
    }

    @Override
    public int div(int a, int b) {
        System.out.println("div方法开始，参数是：" + a + "," + b);
        int r = super.div(a, b);
        System.out.println("div方法结束，结果是：" + r);
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
 * 目前，这个写法，符合了开闭原则，毕竟我们没有修改作者的代码
 *
 *
 * 这样写是有很大的缺点的:
 * 1.工作量太大,我们要为每个方法添加日志功能!
 * // 2.如果ICalc和CalcImpl不是我们自己创建的，是被发现的，手头是没有源代码的!我们不能直接修改源代码!
 * 3.需求如果再次变化呢?比如,客户要求我们使用英文写日志!过了一段时间，还是中文好! !
 */
