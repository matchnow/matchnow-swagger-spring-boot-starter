package com.gswagger.properties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchnowSwaggerServers {
    private List<MatchnowSwaggerServer> external;
    private List<MatchnowSwaggerServer> internal;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MatchnowSwaggerServer {
        private String name;
        private String url;
    }
}
