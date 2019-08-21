package com.example.demo.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> params = new ArrayList<>();
        tokenPar.name("token").description("令牌")
                .modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        params.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
                .paths(PathSelectors.any())
//                .paths(PathSelectors.none())
                .build()
                .globalOperationParameters(params);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("模板项目")
                .description("模板项目-接口文档")
                .termsOfServiceUrl("接口")
                .version("1.0")
                .build();
    }

}