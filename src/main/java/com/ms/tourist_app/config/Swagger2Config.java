package com.ms.tourist_app.config;

import com.ms.tourist_app.application.constants.AppEnv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage(AppEnv.Swagger.basePackage))
                .paths(PathSelectors.regex(AppEnv.Swagger.pathRegex))
                .build()
                .apiInfo(apiEndPointsInfo());
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title(AppEnv.Swagger.title)
                .description(AppEnv.Swagger.description)
                .contact(new Contact(AppEnv.Swagger.nameContact, AppEnv.Swagger.urlContact, AppEnv.Swagger.emailContact))
                .license(AppEnv.Swagger.license)
                .licenseUrl(AppEnv.Swagger.licenseUrl)
                .version(AppEnv.Swagger.version)
                .build();
    }
}