package com.gswagger.controller;

import com.gswagger.MatchnowSwaggerConstants;
import com.gswagger.properties.MatchnowSwaggerProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springdoc.core.Constants.API_DOCS_URL;
import static org.springdoc.core.Constants.SWAGGER_UI_PATH;

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
