package com.gswagger.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MatchnowSwaggerProperties {
    private String redirectPathReplacer;
    private List<MatchnowSwaggerGroup> groups;
    private MatchnowSwaggerGlobalHeaders globalHeaders;
    private MatchnowSwaggerServers servers;
    private MatchnowSwaggerRedirectProperties redirectRules;
}
