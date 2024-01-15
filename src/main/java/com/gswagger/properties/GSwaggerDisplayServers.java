package com.gswagger.properties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GSwaggerDisplayServers {
    private List<SwaggerDisplayServer> external;
    private List<SwaggerDisplayServer> internal;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SwaggerDisplayServer {
        private String name;
        private String url;
    }
}
