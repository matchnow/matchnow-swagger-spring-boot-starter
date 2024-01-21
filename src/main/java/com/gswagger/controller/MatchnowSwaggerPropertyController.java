package com.gswagger.controller;

import com.gswagger.MatchnowSwaggerConstants;
import com.gswagger.properties.MatchnowSwaggerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MatchnowSwaggerPropertyController {
    private final MatchnowSwaggerProperties matchnowSwaggerProperties;

    @GetMapping(MatchnowSwaggerConstants.MATHCNOW_SWAGGER_PROPERTY_URL)
    public ResponseEntity<MatchnowSwaggerProperties> getProperties() {
        return ResponseEntity.ok(matchnowSwaggerProperties);
    }
}
