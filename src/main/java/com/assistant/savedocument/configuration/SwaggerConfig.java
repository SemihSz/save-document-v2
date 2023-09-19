package com.assistant.savedocument.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Component
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build().apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Document Upload & Download Application Api")
                .description("\"Upload & Download Application Api \"")
                .version("1.1.0")
                .contact(new Contact("Semih Sözdinler", "https://github.com/SemihSz", "ibrahim.semih@gmail.com"))
                .build();
    }
//    private SecurityContext securityContext(){
//        return SecurityContext.builder().securityReferences(defaultAuth()).build();
//    }

//    private List<SecurityReference> defaultAuth(){
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
//    }
//
//    private List<RequestParameter> operationParameter() {
//        List<RequestParameter> headers = new ArrayList<>();
//
//        headers.add(new RequestParameterBuilder().name(HttpHeaders.AUTHORIZATION).build());
//        return headers;
//    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("My REST API")
                        .description("Some custom description of API.")
                        .version("1.0"));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new io.swagger.v3.oas.models.security.SecurityScheme().type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                .bearerFormat("JWT").scheme("bearer");
    }
//    @Bean
//    public OpenAPI customOpenApı() {
//        final List<io.swagger.v3.oas.models.servers.Server> servers = domains.stream().map(new io.swagger.v3.oas.models.servers.Server()::url).collect(Collectors.toList());
//
//        return new OpenAPI().servers(servers).components(new Components()
//                .addSecuritySchemes("Authorization", new SecurityScheme().type(SecurityScheme.Type.APIKEY).name("Authorization").description("Bearer jwt token enter")))
//                .security(Collections.singletonList(new SecurityRequirement().addList("Authorization")));
//
//    }

}
