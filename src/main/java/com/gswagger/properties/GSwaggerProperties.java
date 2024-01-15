package com.gswagger.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GSwaggerProperties {
    private GSwaggerGroup welcome;
    private String redirectPathReplacer;
    private List<GSwaggerGroup> groups;
    private GSwaggerGlobalHeaders globalHeaders;
    private GSwaggerDisplayServers displayServers;
}
