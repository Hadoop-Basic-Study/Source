package com.example.config.docs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(@Value("OpenAPI") String appVersion) {
        Info info = new Info().title("Demo API").version(appVersion)
                .description("Spring Boot를 이용한 Demo 웹 애플리케이션 API입니다.")
                .termsOfService("http://swagger.io/terms/")
                .contact(new Contact().name("name").url("https://name.name.name/").email("name@name.name"))
                .license(new License().name("Apache License Version 2.0")
                .url("http://www.apache.org/licenses/LICENSE-2.0"));

        return new OpenAPI()
                .info(info);
    }
}
