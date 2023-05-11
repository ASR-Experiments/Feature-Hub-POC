package com.asr.example.feature.hub.example.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
    info = @Info(
        title = "Feature Hub POC",
        version = "0.0.1-SNAPSHOT",
        description = "Project to demonstrate the capabilities of Feature Hub"),
    security = {
        @SecurityRequirement(name = "api_key")
    }
)
@SecurityScheme(
    type = SecuritySchemeType.OAUTH2,
    name = "api_key",
    in = SecuritySchemeIn.HEADER,
    flows = @OAuthFlows(
        password = @OAuthFlow(
            authorizationUrl = "${auth.issuer.url}",
            tokenUrl = "${auth.login.url}",
            refreshUrl = "${auth.login.url}"
        )
    ))
public class OpenApiConfig {
}
