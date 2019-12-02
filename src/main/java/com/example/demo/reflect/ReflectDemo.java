package com.example.demo.reflect;

import java.lang.reflect.Proxy;

/**
 * @author zhaoyu
 * @date 2019-07-22
 */
public class ReflectDemo implements ReflectDemoInterface {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void process() {
        System.out.println("[" + this.name + "  " + this.age + "]");
    }

    public void printStr(String name, int num) {
        System.out.println(" name is " + name + ", num is" + num);
    }


    public static void main(String[] args) throws Exception {
        Class<?> aClass = Class.forName("com.example.demo.reflect.ReflectDemo");
        ReflectDemo reflectDemo = (ReflectDemo) aClass.newInstance();

        ReflectDemoInterface reflectDemoInterface =
                (ReflectDemoInterface) Proxy.newProxyInstance(ReflectDemoInterface.class.getClassLoader(),
                        new Class[]{ReflectDemoInterface.class},
                        new NormalHandler(reflectDemo));


        reflectDemoInterface.process();

        reflectDemo.printStr("rainzhao", 100);
    }


}
