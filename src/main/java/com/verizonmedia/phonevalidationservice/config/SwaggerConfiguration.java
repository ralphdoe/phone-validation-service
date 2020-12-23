package com.verizonmedia.phonevalidationservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.OAS_30)
        .select()
        .apis(RequestHandlerSelectors.basePackage(
            "com.verizonmedia.phonevalidationservice.controllers"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Phone Validation Service")
        .description("Validates if the numbers are valid or invalid.")
        .version("1.0.0")
        .termsOfServiceUrl("Terms of Service URL")
        .contact(new Contact("Contact Name", "URL Contact", "ralphdoe12@gmail.com"))
        .license("license")
        .licenseUrl("license url")
        .build();
  }
}