package com.gswagger.properties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchnowSwaggerGlobalHeaders {
    private List<MatchnowSwaggerHeader> external;
    private List<MatchnowSwaggerHeader> internal;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MatchnowSwaggerHeader {
        private String name;
        private String example;
    }
}
