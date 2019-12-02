package com.example.demo.chat.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.chat.mapper.UsersMapper;
import com.example.demo.chat.model.JsonResult;
import com.example.demo.chat.model.User;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhaoyu
 * @date 2019-05-18
 */
@RestController
@RequestMapping("u")
public class HelloController {

    @Autowired
    private UsersMapper usersMapper;

    @PostMapping("/registOrLogin")
    public JsonResult hello(@RequestBody User user) {
        if(StringUtils.isNullOrEmpty(user.getUsername()) || StringUtils.isNullOrEmpty(user.getPassword())) {
            return JsonResult.errorMsg("用户名或密码不能为空");
        }


        return JsonResult.ok();
    }
}
