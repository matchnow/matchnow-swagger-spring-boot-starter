package com.gswagger.utils;

import com.gswagger.properties.GSwaggerProperties;
import com.gswagger.properties.GSwaggerRedirectProperties;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
public class DefaultRedirectPathReplacer implements GSwaggerRedirectPathReplacer {
    private final GSwaggerProperties properties;

    @Override
    public String replace(String path) {
        List<GSwaggerRedirectProperties.GSwaggerRedirectRule> rules = new ArrayList<>();
        if (!isEmpty(properties.getRedirectRules().getExternal())) {
            rules.addAll(properties.getRedirectRules().getExternal());
        }

        if (!isEmpty(properties.getRedirectRules().getInternal())) {
            rules.addAll(properties.getRedirectRules().getInternal());
        }

        for (GSwaggerRedirectProperties.GSwaggerRedirectRule rule : rules) {
            path = path.replace(rule.getFrom(), rule.getTo());
        }
        
        return path;
    }
}