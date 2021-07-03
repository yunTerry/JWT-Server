package com.terry.Service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@EnableOpenApi
@Configuration
public class Swagger {

    @Bean
    public Docket createRestApi(Environment environment) {
        ApiInfo apiInfo = new ApiInfoBuilder().title("JWT Server").build();
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.terry.Controller"))
                .build();
    }

}