package com.soft.n_proxy.supplement;

import java.lang.reflect.Method;

class Person {

    public void eat() {
        System.out.println("我吃！！");
    }
    public void eat(String food) {
        System.out.println("我吃 " + food);
    }
    public void run() {
        System.out.println("我跑！！");
    }
}

class Cat {
    public void eat() {
        System.out.println("猫吃");
    }
}

public class Test {
    public static void main(String[] args) throws Exception {

        // 利用反射机制调用类的方法

        // 1.获取类的字节码,字节码是根据源代码生成的，所以源代码中的信息，字节码中也有！ (泛型)
        Class clazz = Class.forName("com.soft.n_proxy.supplement.Person");

        // 2.利用反射机制创建一个对象, 以下的api,就是利用反射机制，调用类的无参构造器来实例化对象的!
        Object obj = clazz.newInstance();
        System.out.println(obj);

        //3、反射出字节码中的某个方法
//        Method m = clazz.getDeclaredMethod("eat");
        Method m = clazz.getDeclaredMethod("eat", String.class);

        //4、利用反射机制调用方法
        // 把m所代表的方法，当做obj对象的方法来调用!
//        m.invoke(obj);
        m.invoke(obj, "拉面");

        /*
        注意，这里的是不对的——类型不对
         */
//        Cat cat = new Cat();
//        m.invoke(cat);
        /*
        注意，这里可以，虽然不是一个对象，但是类型对
         */
//        Person p2 = new Person();
//        m.invoke(p2);

        // 天下大事必作于细 天下难事必作于易

        /*
        1、new
        2、反射
        3、克隆
        4、反序列化
        前两种会调用构造器，后两种不调用
         */

        //正常调用
//        Person p = new Person();
//        p.eat();
    }
}
