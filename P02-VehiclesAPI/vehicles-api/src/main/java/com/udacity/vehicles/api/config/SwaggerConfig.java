package com.udacity.vehicles.api.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
@ApiResponses(value = {
        @ApiResponse(code=400, message = "This is a bad request, please follow the API documentation for tips."),
        @ApiResponse(code=401, message = "Due to security constraints, you are DENIED!"),
        @ApiResponse(code=500, message = "The server is down. Please lift him up.")
})
public class SwaggerConfig {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo(){
        return new ApiInfo(
                "Vehicles API",
                "This API creates, views, deletes and updates vehicles.",
                "1.0",
                "http://www.udacity.com/tos",
                new Contact("Christian Rathgeber", "www.udacity.com", "poorguy@udacity.com"),"License of API", "http://www.udacity.com/license", Collections.emptyList()

        );
    }

}
