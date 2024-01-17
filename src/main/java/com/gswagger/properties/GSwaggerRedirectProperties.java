package com.gswagger.properties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GSwaggerRedirectProperties {
    private List<GSwaggerRedirectRule> external;
    private List<GSwaggerRedirectRule> internal;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GSwaggerRedirectRule {
        private String from;
        private String to;
    }
}
