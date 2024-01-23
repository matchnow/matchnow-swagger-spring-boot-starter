package com.gswagger.controller;

import com.gswagger.MatchnowSwaggerConstants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MatchnowSwaggerPropertyController {
    private final String swaggerUiPath;
    private final String apiDocPath;

    @GetMapping(value = MatchnowSwaggerConstants.MATHCNOW_SWAGGER_PROPERTY_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public MatchnowSwaggerServiceProperties getProperties() {
        MatchnowSwaggerServiceProperties properties = new MatchnowSwaggerServiceProperties();
        properties.swaggerUiPath = swaggerUiPath;
        properties.apiDocPath = apiDocPath;

        return properties;
    }

    @Getter
    public static class MatchnowSwaggerServiceProperties {
        private String swaggerUiPath;
        private String apiDocPath;
    }
}
