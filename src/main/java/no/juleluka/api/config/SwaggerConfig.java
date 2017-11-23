package no.juleluka.api.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private String authorizeEndpointUrl = "https://juleluka.eu.auth0.com/authorize";
    private String tokenEndpointUrl = "https://juleluka.eu.auth0.com/oauth/token";
    private String clientId = "NklpaPL2eL76x1o9bg7pk6GA7nGPUTqG";
    private String clientSecret = "7eqCcQmlGOfoXY32gLy7pXf7Fvjth6ERxh2YPSNdG9dpXzzEc1vt_2rQYzU19AAb";
    private String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IlFqTkJPRU14TWpRek1UVXlOVEV4UlRrNE5VSTFNRGN3TlVVNE5UTTNPVEUzTURSR01FTXpPUSJ9.eyJpc3MiOiJodHRwczovL2p1bGVsdWthLmV1LmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw1YTA1YjM3OGRhMGY0MjdlOTgwZjM4NjAiLCJhdWQiOlsiaHR0cHM6Ly9hcGkuanVsZWx1a2Eubm8iLCJodHRwczovL2p1bGVsdWthLmV1LmF1dGgwLmNvbS91c2VyaW5mbyJdLCJpYXQiOjE1MTEyMTI2OTksImV4cCI6MTUxMTI5OTA5OSwiYXpwIjoiQjJ6bWl1b0Z6aGV5TDRmZFcxd0ZkZko3bDM3MXZRVm4iLCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIiwiZ3R5IjoicGFzc3dvcmQifQ.PrN8c3KxH186Yj91vivy8Hmzms2Ri-KLstGa_Ek4dLDPfD2aeXoLfFgzFyEtPMj2diLch4wBJ-6boUo-kYLTvYY8i-MuaPjkQNeXWSSp4R0SDOO6ORQCO2Gz0UCIIGKcAQtggIjjcwG7KlScX-ZG_6eg6zDaN9uRxYiu_v7OA2PSEGpMsCyHbbUA_Ct0EKgZ863CRSJameujBiO185WOnQFFizlfMkIbAM-9OXQXBIi21U2w4HngaoPyPziNluducK6cLkRHBbN3Bl_wWTbLtF26ZOOj2s2Yz1bNQyTRPNmHswf-nU8AmqRQutf-0bRArckAvPooHPDYUznwSqbWgA";

    private final ImmutableList<AuthorizationScope> SCOPES = ImmutableList.of(
        new AuthorizationScope("read:calendars", "Read your calendars"),
        new AuthorizationScope("edit:calendars", "Edit your calendars"),
        new AuthorizationScope("admin:calendars", "Administrator access")
    );

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(newArrayList(oauthSecurityScheme()))
//                .securityContexts(newArrayList(securityContext()))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Juleluka API",
                "API for juleluka",
                "API TOS",
                "Terms of service",
                new Contact("UDP AS", "http://udp.no", "post@udp.no"),
                "License of API", "API license URL", Collections.emptyList());
    }
/*
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/anyPath.*"))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference("access_token", authorizationScopes));
    }
*/
    private SecurityScheme oauthSecurityScheme() {
        return new OAuthBuilder()
                .name("OAuth2")
                .scopes(SCOPES)
                .grantTypes(grantTypes())
                .build();
    }

    List<GrantType> grantTypes() {
        List<GrantType> grantTypes = new ArrayList<>();
//        TokenRequestEndpoint tokenRequestEndpoint = new TokenRequestEndpoint(authorizeEndpointUrl, "cliiiient_id", "client_zecret");
//        TokenEndpoint tokenEndpoint = new TokenEndpoint(tokenEndpointUrl, "token");
//        grantTypes.add(new AuthorizationCodeGrant(tokenRequestEndpoint, tokenEndpoint));
        LoginEndpoint loginEndpoint = new LoginEndpoint(authorizeEndpointUrl);
        grantTypes.add(new ImplicitGrant(loginEndpoint, "access_token"));
        return grantTypes;
    }

    @Bean
    SecurityConfiguration swaggerSecurity() {
        return new SecurityConfiguration(
                clientId,
                clientSecret,
                "sdf", // Must be set, ref https://github.com/springfox/springfox/issues/1537,
                "Juleluka Swagger",
                "Bearer " + accessToken,
                ApiKeyVehicle.HEADER,
                "Authorization",
                " "
        );
    }

    /*
    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration(
                "validatorUrl",// url
                "none",       // docExpansion          => none | list
                "alpha",      // apiSorter             => alpha
                "schema",     // defaultModelRendering => schema
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
                true,        // enableJsonEditor      => true | false
                true,         // showRequestHeaders    => true | false
                60000L);      // requestTimeout => in milliseconds, defaults to null (uses jquery xh timeout)
    }
*/
}