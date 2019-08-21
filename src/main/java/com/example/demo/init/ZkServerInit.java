package com.example.demo.init;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ZkServerInit implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent context) {
        if (null == context.getApplicationContext().getParent()) {
            System.err.println("初始化zkServer...");
            // 初始化逻辑...
            System.err.println("初始化zkServer完成...");
        }
    }
}
