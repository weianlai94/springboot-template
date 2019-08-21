package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author weianlai
 * @date 2019-01-14 11:05
 */
@Component
public class MyCommandLineRunner implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        System.out.println("项目启动后，我也会被执行!!!");
    }
}
