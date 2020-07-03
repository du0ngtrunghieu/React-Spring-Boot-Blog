package com.trunghieu.common.config;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created on 9/4/2020.
 * Class: SwaggerConfig.java
 * By : Admin
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${version.number}")
    private String version;
    @Value("${description.text}")
    private String description;
    @Value("${title.text}")
    private String title;
    @Value("${name.text}")
    private String name;
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

    @Bean
    public Docket swaggerSpringfoxDocket() {
        Contact contact = new Contact(
                name,
                "https://bap-software.net/",
                "");

        List<VendorExtension> vext = new ArrayList<>();
        ApiInfo apiInfo = new ApiInfo(
                title,
                description,
                version,
                "https://simple-it.net/",
                contact,
                "MIT",
                "https://simple-it.net",
                vext);

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .pathMapping("/")
                .apiInfo(ApiInfo.DEFAULT)
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .ignoredParameterTypes(Pageable.class)
                .ignoredParameterTypes(BindingResult.class)
                .ignoredParameterTypes(java.sql.Date.class)
                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
                .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .useDefaultResponseMessages(false);

        docket = docket.select()
                .paths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build();

        return docket;
    }


    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("JWT", authorizationScopes));
    }

}
