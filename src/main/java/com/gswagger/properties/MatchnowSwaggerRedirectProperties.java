package com.gswagger.properties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchnowSwaggerRedirectProperties {
    private List<MatchnowSwaggerRedirectRule> external;
    private List<MatchnowSwaggerRedirectRule> internal;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MatchnowSwaggerRedirectRule {
        private String from;
        private String to;
    }
}
