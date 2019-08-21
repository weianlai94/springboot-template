package com.example.demo.dao.mapper;

import com.example.demo.common.dto.UserDTO;
import com.example.demo.common.entity.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

public interface UserMapper extends Mapper<User> {

    UserDTO getUserInfo(int id);

    Map getUser(Integer id);

}