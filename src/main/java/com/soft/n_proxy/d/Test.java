package com.soft.n_proxy.d;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

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

class MyHandler implements InvocationHandler {

    private Object target;

    public MyHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        System.out.println("呵呵哒！！");
//        System.out.println(method.getName() + ": " + Arrays.toString(args));
        System.out.println(method.getName() + "开始，参数是: " + Arrays.toString(args));
        //利用反射机制，调用方法! !
        //把method所代表的方法，当做c对象的方法调用，参数是args  //method.invoke(c,args);
        Object r = method.invoke(target, args);// c为什么报错？
        System.out.println(method.getName() + "结束，结果是: " + r);
        return r;
    }
}

public class Test {
    public static void main(String[] args) {
        //类加载器，其实获取谁的都行，条条大路通罗马
//        System.out.println(Test.class.getClassLoader() == MyHandler.class.getClassLoader());

        //真实对象，也叫作目标对象
        ICalc c = new CalcImpl();
//        System.out.println(c.add(3,3));

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

            而使用动态代理api创建对象时，加载哪个字节码呢? 加载的字节码就会在运行期动态生成的字节码,这个动态生成的字节码是不需要源代码的! !

            还有问题是:字节码确实可以自动生成，那么动态代理api动态生成的字节码的内容,是根据什么生成的呢?
            这恰恰就是根据第二个参数生成的! ! 动态生成代理，会生成-个实现了目标接口的类的字节码，
            在本例中，就是生成一个实现了ICalc接口的类的字节码!
         */
        Class[] interfaces = {ICalc.class};

        /*
            第三个参数:调用处理器，InvocationHandler
            我们已经知道，动态代理会加载自己动态生成的字节码，且这个字节码是根据某个几口生成的，在本例中就是根据ICalc接口生成的
            生成的是实现了ICalc接口的类的字节码。
            问题是:实现一个接口，就要实现其中的抽象方法，那么动态代理生成的字节码，实现了ICalc接口，势必就要实现其中的add. sub、 mul. diy方法!
            这些方法被实现的方法体都是什么内容呢? ? ?这恰恰就是由第3个参数决定的! MyHandler类的invoke方法，就是方法体的内容，可以这样理解:

            class 我是动态生成的那个类 implements IClac{
            add() {
                new MyHandler().invoke(); //不是每次都new ，这个是伪代码
            }
            sub() {
                new MyHandler().invoke();
            }
            mul() {
                new MyHandler().invoke();
            }
            div() {
                new MyHandler().invoke();
            }


         */
        //代理对象
        ICalc proxy = (ICalc)Proxy.newProxyInstance(cl, interfaces, new MyHandler(c));

        //总之，你就记住，对代理对象的方法的调用，都统统会进入调用处理器中! 而不会直接进入真是的方法!
        int x = proxy.add(1, 2);
//        proxy.sub(1,2);
//        proxy.mul(1,2);
//        proxy.div(1,2);

        System.out.println(x);
    }

    /*
        这样写是已经克服了之前的缺点了
        1.工作量一点都不打，很随意! !
        2.需求如果再次变化呢?比如，客户要求我们使用英文写日志!我们只需要该一个地方即可!
        3.如果需求是这样呢?星期1,3,5中文，2,4,6英文。

        就目前这个写法还是有缺点的:太复杂了!
     */
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
