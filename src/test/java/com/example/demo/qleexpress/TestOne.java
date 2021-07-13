package com.example.demo.qleexpress;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author: zhaoyu
 * @date: 2021/6/17
 * @description:
 */
public class TestOne {

    @Test
    public void testOne() throws Exception {

        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("a", 1);
        context.put("b", 2);
        context.put("c", 3);
        String express = "a+b*c";
        Object r = runner.execute(express, context, null, true, false);
        System.out.println(r);
    }

    /**
     * 三元运算符测试
     *
     * @throws Exception
     */
    @Test
    public void testSanyuan() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("a", 1);
        context.put("b", 2);
        String express = "a>b?a:b";
        Object r = runner.execute(express, context, null, true, false);
        Assert.assertEquals(2, r);
    }

    @Test
    public void testForLoop() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        String express = "n=10;sum=0;" +
                "for(i=0;i<n;i++)" +
                "{sum=sum+i;}" +
                " return sum;";
        Object r = runner.execute(express, context, null, true, false);
        Assert.assertEquals(45, r);
    }


    /**
     * 测试注释
     *
     * @throws Exception
     */
    @Test
    public void testComment() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        String express = "n=10;sum=0; /**测试for循环**/" +
                "for(i=0;i<n;i++)" +
                "{sum=sum+i;}" +
                " return sum;";
        Object r = runner.execute(express, context, null, true, false);
        Assert.assertEquals(45, r);
    }

    /**
     * 测试equals
     *
     * @throws Exception
     */
    @Test
    public void testEquals() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        String express = "a=1;b=1;result=false;" +
                "if (a == b)" +
                "{result=true}" +
                " return result;";
        Object r = runner.execute(express, context, null, true, false);
        System.out.println(r);
        Assert.assertEquals(true, r);

        String express2 = "a=1;b=1;result=false;" +
                "if (a != b)" +
                "{result=true}" +
                "return result;";
        Object r2 = runner.execute(express2, context, null, true, false);

        System.out.println(r2);
        Assert.assertEquals(false, r2);
    }

    /**
     * continue 测试
     *
     * @throws Exception
     */
    @Test
    public void testContinue() throws Exception {
        String express = "a = 10 ; for (i=0;i<a;i++){if(i==4){continue ; } println(i) ;} ";
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        ExpressRunner runner = new ExpressRunner(false, false);
        Object res = runner.execute(express, context, null, true, false);
    }

    /**
     * 测试list 的 add 和 for
     *
     * @throws Exception
     */
    @Test
    public void testListAddAndFor() throws Exception {
        String express = "keys = new ArrayList(); keys.add(1); keys.add(2); " +
                "for(i = 0; i<keys.size();i++){" +
                "println(keys.get(i));" +
                "}";
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        ExpressRunner runner = new ExpressRunner(false, false);
        Object res = runner.execute(express, context, null, true, false);
    }

    /**
     * 数组初始化和循环
     *
     * @throws Exception
     */
    @Test
    public void testArrayAddAndFor() throws Exception {
        String express = "deviceNames = [\"ng\",\"si\",\"umid\",\"ut\",\"mac\",\"imsi\",\"imei\"];" +
                "for(i=0;i<deviceNames.length;i++){" +
                "println(deviceNames[i])" +
                "}";

        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        ExpressRunner runner = new ExpressRunner(false, false);
        Object res = runner.execute(express, context, null, true, false);
    }

    /**
     * hashmap测试
     *
     * @throws Exception
     */
    @Test
    public void testHashMap() throws Exception {
        String express = "map = new HashMap();map.put('a',1);map.put('b',2);" +
                "keySet = map.keySet();" +
                "objArr = keySet.toArray();" +
                "for(i = 0; i < objArr.length; i++){" +
                "key=objArr[i];" +
                "print(key + '   ');println(map.get(key));" +
                "}";
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        ExpressRunner runner = new ExpressRunner(false, false);
        Object res = runner.execute(express, context, null, true, false);
    }

    @Test
    public void testAnd() throws Exception {
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("a", false);
        context.put("b", true);
        String express = "a and b";
        ExpressRunner runner = new ExpressRunner(false, false);
        Object res = runner.execute(express, context, null, true, false);
        System.out.println(res);
    }

    /**
     * 测试添加方法。并调用
     *
     * @throws Exception
     */
    @Test
    public void testAddFunction() throws Exception {
        String express = "myPrintTest(\"1234\"); myPrintTestDouble(29)";

        ExpressRunner runner = new ExpressRunner();
        runner.addFunctionOfClassMethod("myPrintTest", FunctionAddTest.class.getName(), "printTest",
                new String[]{"String"}, null);
        runner.addFunctionOfClassMethod("myPrintTestDouble", FunctionAddTest.class.getName(), "printDouble",
                new String[]{"double"}, null);

        Object execute = runner.execute(express, null, null, true, false);

        System.out.println(execute);
    }

    /**
     * 测试操作符
     *
     * @throws Exception
     */
    @Test
    public void testOperator() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> defaultContext = new DefaultContext<>();
        runner.addOperator("myjoin", new JoinOperator());
        Object r = runner.execute("1 myjoin 2 myjoin 3", defaultContext, null, false, false);
        System.out.println(r);
    }

    @Test
    public void addFunction() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<>();
        runner.addFunction("join", new JoinOperator());
        Object r = runner.execute("join(1,2,3)", context, null, false, false);
        System.out.println(r);
    }

    /**
     * 用于将一个用户自己定义的对象(例如Spring对象)方法转换为一个表达式计算的函数
     *
     * @throws Exception
     */
    @Test
    public void addServiceFunction() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<>();
        runner.addFunctionOfServiceMethod("打印", System.out, "println", new String[]{"String"}, null);

        String express = "打印(\"你好\")";
        Object execute = runner.execute(express, context, null, false, false);
        System.out.println(execute);
    }



}
