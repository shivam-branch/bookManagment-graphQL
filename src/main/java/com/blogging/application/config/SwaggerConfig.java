package com.blogging.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

  Parameter authHeader =
      new ParameterBuilder()
          .parameterType("header")
          .name("Authorization")
          .modelRef(new ModelRef("string"))
          .build();
  @Value("${swagger.host.name}")
  private String hostName;

  @Bean
  public Docket productApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .host(hostName)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.blogging.application"))
        .paths(regex("/api.*"))
        .build()
        .apiInfo(metaInfo())
        .globalResponseMessage(RequestMethod.GET, newArrayList())
        .globalResponseMessage(RequestMethod.POST, newArrayList())
        .globalResponseMessage(RequestMethod.PUT, newArrayList())
        .globalResponseMessage(RequestMethod.DELETE, newArrayList())
        .globalOperationParameters(Collections.singletonList(authHeader));
  }

  private List<ResponseMessage> newArrayList() {
    List<ResponseMessage> responseMessages = new ArrayList<>();
    responseMessages.add(
        new ResponseMessageBuilder()
            .code(401)
            .message(
                "Unauthorized "
                    + "{\n"
                    + "    \"error\": null,\n"
                    + "    \"error_description\":null\n"
                    + "}")
            .build());
    responseMessages.add(
        new ResponseMessageBuilder()
            .code(404)
            .message(
                "Not Found"
                    + "{\n"
                    + "    \"timestamp\": null,\n"
                    + "    \"status\": 404,\n"
                    + "    \"error\": \"Not Found\",\n"
                    + "    \"message\": \"No message available\",\n"
                    + "    \"path\": path\n"
                    + "}")
            .build());
    responseMessages.add(
        new ResponseMessageBuilder()
            .code(500)
            .message(
                "InternalServerError"
                    + "{\n"
                    + "    \"message\": string,\n"
                    + "    \"timestamp\": null,\n"
                    + "    \"path\": path\n"
                    + "}")
            .build());
    return responseMessages;
  }

  private ApiInfo metaInfo() {
    return new ApiInfo(
        "Blogging Application API",
        "Blogging Application API",
        "1.0",
        "Terms of Service",
        new Contact("Blogging Application", "", ""),
        "Apache License Version 2.0",
        "https://www.apache.org/licesen.html",
        new ArrayList<>());
  }
}
