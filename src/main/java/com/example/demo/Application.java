package com.example.demo;

import com.github.pagehelper.PageHelper;
//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.Properties;

@SpringBootApplication
@EnableSwagger2
@MapperScan("com.example.demo.dao.mapper")
@EnableScheduling
public class Application {

//	@Bean
//	public PageHelper pageHelper() {
//		PageHelper pageHelper = new PageHelper();
//		Properties properties = new Properties();
//		properties.setProperty("offsetAsPageNum", "true");
//		properties.setProperty("rowBoundsWithCount", "true");
//		properties.setProperty("reasonable", "true");
//		properties.setProperty("dialect", "mysql");
//		pageHelper.setProperties(properties);
//		return pageHelper;
//	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

