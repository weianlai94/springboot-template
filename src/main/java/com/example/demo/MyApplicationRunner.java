package com.example.demo;

import com.example.demo.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author weianlai
 * @date 2019-01-14 11:01
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("项目一启动就会执行我 ^_^");
    }

}
