package com.example.demo.rpcsimple.bootstrap;

import com.example.demo.rpcsimple.client.RpcClientHelper;
import com.example.demo.rpcsimple.serverdemo.ServerInfoTest3;
import com.example.demo.rpcsimple.serverdemo.ServerInfoTest4;
import com.example.demo.rpcsimple.serverdemo.model.DataRequest;
import com.example.demo.rpcsimple.serverdemo.model.DataResponse;
import com.example.demo.rpcsimple.serverdemo.model.MyResponse;
import com.example.demo.rpcsimple.serverdemo.ServerInfoTest;
import com.example.demo.rpcsimple.serverdemo.ServerInfoTest2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaoyu
 * @date 2019-06-01
 */
@RestController
public class RpcTest {

    @GetMapping("/hello")
    public String get() {
        String test = RpcClientHelper.getClient(ServerInfoTest.class).test();
        System.out.println("RpcTest get times" + System.currentTimeMillis());
        return test;
    }


    @GetMapping("/hello2")
    public String get2() {
        String test = RpcClientHelper.getClient(ServerInfoTest2.class).getData("hahah", 2);
        System.out.println("RpcTest get times" + System.currentTimeMillis());

        return test;
    }

    @GetMapping("hello3")
    public MyResponse get3() {
        MyResponse myResponse = RpcClientHelper.getClient(ServerInfoTest3.class).getMyResponse("zhaoyu", 25);
        return myResponse;
    }

    @GetMapping("hello4")
    public DataResponse get4() {
        DataRequest dataRequest = new DataRequest();
        dataRequest.setCode(100);
        dataRequest.setData("test4");
        DataResponse test = RpcClientHelper.getClient(ServerInfoTest4.class).test(dataRequest);
        return test;
    }

    @GetMapping("test")
    public void getTest(@RequestParam("user") String userName, @RequestParam(value = "age", required = false) Integer age) {
        System.out.println(userName);

        System.out.println(age);
    }


}
