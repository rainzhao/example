package com.example.demo.chat.mapper;

import com.example.demo.chat.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhaoyu
 * @date 2019-05-18
 */
@Mapper
public interface UsersMapper {
    @Select("SELECT * FROM users")
    List<User> getAllUserInfo();
}
