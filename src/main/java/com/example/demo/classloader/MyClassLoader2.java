package com.example.demo.classloader;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author pengzhiheng
 * @date 2019-07-24
 *
 *
 * @description 自定义类加载器，打破双亲委派机制，让动态字节码生成的类可以重复加载
 */
public class MyClassLoader2 extends ClassLoader {

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {

        if (StringUtils.isBlank(name)) {
            throw new ClassNotFoundException();
        }

        String path = this.getPath(name) ;

        byte[] ret = this.getClassData(path) ;
        if (ret == null) {
            throw new ClassNotFoundException("读取".concat(path).concat("失败."));
        }

        return defineClass(name, ret,0, ret.length) ;
    }

    private byte[] getClassData(String path) {
        System.out.println("Path: - ".concat(path));
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(path)) ;
            ByteArrayOutputStream baos = new ByteArrayOutputStream() ;
            byte[] buffer = new byte[1024] ;
            while (inputStream.read(buffer) != -1) {
                baos.write(buffer, 0, buffer.length);
            }

            return baos.toByteArray() ;
        } catch (Exception e) {
            System.out.println("读取class文件流失败" + e);
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    System.out.println("关闭class文件流失败" + e);
                }
            }
        }
        return null ;
    }

    private String getPath(String className) {
        return className.replace(".", File.separator).concat(".class") ;
    }

    public static void main(String[] args) {
        MyClassLoader2 myClassLoader2 = new MyClassLoader2();
        Class<?> testClass = null;
        try {
            testClass = myClassLoader2.loadClass("com.example.demo.classloader.Test123");
            Object object = testClass.newInstance();
            System.out.println(object.getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}