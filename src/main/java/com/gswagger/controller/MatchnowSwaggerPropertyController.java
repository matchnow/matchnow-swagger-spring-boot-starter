package com.gswagger.controller;

import com.gswagger.MatchnowSwaggerConstants;
import com.gswagger.properties.MatchnowSwaggerProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springdoc.core.Constants.API_DOCS_URL;
import static org.springdoc.core.Constants.SWAGGER_UI_PATH;

@RestController
@ConditionalOnBean(MatchnowSwaggerProperties.class)
@RequiredArgsConstructor
public class MatchnowSwaggerPropertyController {
    private final MatchnowSwaggerProperties matchnowSwaggerProperties;

    @Value(SWAGGER_UI_PATH)
    private String swaggerUiPath;
    @Value(API_DOCS_URL)
    private String apiDocPath;

    @GetMapping(MatchnowSwaggerConstants.MATHCNOW_SWAGGER_PROPERTY_URL)
    public ResponseEntity<MatchnowSwaggerServiceProperties> getProperties() {
        return ResponseEntity.ok(MatchnowSwaggerServiceProperties.builder()
                .swaggerUiPath(swaggerUiPath)
                .apiDocPath(apiDocPath)
                .matchnowSwaggerProperties(matchnowSwaggerProperties)
                .build());
    }

    @Getter
    @Builder
    public static class MatchnowSwaggerServiceProperties {
        private String swaggerUiPath;
        private String apiDocPath;
        private MatchnowSwaggerProperties matchnowSwaggerProperties;
    }
}
