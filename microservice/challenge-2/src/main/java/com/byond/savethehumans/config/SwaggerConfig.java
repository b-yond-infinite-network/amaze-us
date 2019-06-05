package com.byond.savethehumans.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static com.google.common.collect.Lists.newArrayList;

import java.util.Collections;

/*

    Swagger configuration is here.

 */


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.byond.savethehumans.controller"))
                .paths(PathSelectors.ant("/*"))
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, newArrayList(new ResponseMessageBuilder().code(500)
                                .message("500 message")
                                .responseModel(new ModelRef("Error"))
                                .build(),
                        new ResponseMessageBuilder().code(403)
                                .message("Forbidden!!!!!")
                                .build()));
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("B-Yond Microservices Challenge #2 - Save the Humans", "This service gonna save the world!", "API TOS", "Terms of service", new Contact("Amir Sedighi", "com.byond", "myeaddress@company.com"), "License of API", "API license URL", Collections.emptyList());
        return apiInfo;
    }
}

