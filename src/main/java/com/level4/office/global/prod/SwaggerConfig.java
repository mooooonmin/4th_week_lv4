package com.level4.office.global.prod;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    private static final String JWT_AUTH_SCHEME_NAME = "JWTAuth";
    private static final String REFRESH_TOKEN_SCHEME_NAME = "RefreshToken";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(JWT_AUTH_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(JWT_AUTH_SCHEME_NAME)
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Authorization"))
                        .addSecuritySchemes(REFRESH_TOKEN_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(REFRESH_TOKEN_SCHEME_NAME)
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Refresh-Token")))
                .addSecurityItem(new SecurityRequirement().addList(JWT_AUTH_SCHEME_NAME))
                .addSecurityItem(new SecurityRequirement().addList(REFRESH_TOKEN_SCHEME_NAME))

                .schema("LoginRequestDto", new Schema<>()
                        .type("object")
                        .addProperty("email", new StringSchema().example("email@example.com"))
                        .addProperty("password", new StringSchema().example("123123"))
                )
                .path("/api/user/login", new PathItem()
                        .post(new Operation()
                                .summary("로그인")
                                .description("사용자 로그인을 위한 엔드포인트")
                                .tags(Collections.singletonList("auth"))
                                .operationId("loginUser")
                                .requestBody(new RequestBody()
                                        .content(new Content()
                                                .addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                                                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/LoginRequestDto"))))
                                )
                                .responses(new ApiResponses()
                                        .addApiResponse("200", new ApiResponse().description("로그인 성공"))
                                        .addApiResponse("401", new ApiResponse().description("인증 실패"))
                                )));
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("user-auth")
                .pathsToMatch("/api/user/**")
                .build();
    }

    @Bean
    public GroupedOpenApi courseApi() {
        return GroupedOpenApi.builder()
                .group("user-course")
                .pathsToMatch("/api/courses/**")
                .build();
    }

    @Bean
    public GroupedOpenApi instructorsApi() {
        return GroupedOpenApi.builder()
                .group("user-instructor")
                .pathsToMatch("/api/instructors/**")
                .build();
    }

}
