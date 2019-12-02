package com.example.demo.classloader;


import org.apache.ibatis.javassist.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author pengzhiheng
 * @date 2019-07-24
 * @description 通过动态字节码方式生成接口--不引用包的方式
 */
public class DynamicGenerateClass {

    /**
     * 接口名及其对应的class文件
     */
    public static Map<String,Class<?>> ifaceClasss = new ConcurrentHashMap<>();

    /**
     * 接口名对应的服务名
     */
    public static Map<String,String> ifaceClientNames = new ConcurrentHashMap<>();



    /**
     * 生成或修改动态字节码接口
     *
     * @param iface 接口名
     * @param methods 方法
     * @throws Exception
     */
    public static Class<?> generateOrUpdateIface(String iface, List<String> methods) throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass cls ;

        if(ifaceClasss.get(iface) != null) {
            //如果已经存在
            try {
                cls = pool.get(ifaceClasss.get(iface).getName());
                if(cls.isFrozen()) {
                    cls.defrost();
                }
                //删除里面所有的方法
                for (CtMethod declaredMethod : cls.getDeclaredMethods()) {
                    cls.removeMethod(declaredMethod);
                }
            } catch (NotFoundException e) {
                cls = pool.makeInterface(iface);
            }
        } else {
            cls = pool.makeInterface(iface);
        }
        //新增所有方法
        for (String method : methods) {
            CtMethod m = CtNewMethod.make("public com.example.demo.common.Proto "+method+"(com.alibaba.fastjson.JSONObject params);",cls);

            cls.addMethod(m);
        }
        //由于cls.toClass会加载class文件，同一个class默认只能加载一次，因此需要自定义类加载器打破双亲委派机制加载
        Class clazz = cls.toClass(new MyClassLoader2());
        ifaceClasss.put(iface,clazz);

        return clazz;
    }

    public static void main(String[] args) throws Exception {
        Class<?> getWin = generateOrUpdateIface("com.rainzhao.test", Arrays.asList("getWin"));

        Method[] methods = getWin.getMethods();

        System.out.println(getWin);

    }

}
