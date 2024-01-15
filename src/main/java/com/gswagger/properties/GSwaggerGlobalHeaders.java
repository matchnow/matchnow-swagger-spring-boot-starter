package com.gswagger.properties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GSwaggerGlobalHeaders {
    private List<GSwaggerHeader> external;
    private List<GSwaggerHeader> internal;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GSwaggerHeader {
        private String name;
        private String example;
    }
}
