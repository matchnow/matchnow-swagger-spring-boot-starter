package com.gswagger.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GSwaggerProperties {
    private String redirectPathReplacer;
    private List<GSwaggerGroup> groups;
    private GSwaggerGlobalHeaders globalHeaders;
    private GSwaggerServers servers;
}
