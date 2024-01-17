package com.gswagger.utils;

import com.gswagger.properties.GSwaggerProperties;
import com.gswagger.properties.GSwaggerRedirectProperties;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
public class DefaultRedirectPathReplacer implements GSwaggerRedirectPathReplacer {
    private final GSwaggerProperties properties;

    @Override
    public String replace(String path) {
        GSwaggerRedirectProperties redirectRules = properties.getRedirectRules();
        if (Objects.nonNull(redirectRules)) {
            path = replaceByRules(path, redirectRules);
        }
        return path;
    }

    private static String replaceByRules(String path, GSwaggerRedirectProperties redirectRules) {
        List<GSwaggerRedirectProperties.GSwaggerRedirectRule> rules = new ArrayList<>();
        if (!isEmpty(redirectRules.getExternal())) {
            rules.addAll(redirectRules.getExternal());
        }

        if (!isEmpty(redirectRules.getInternal())) {
            rules.addAll(redirectRules.getInternal());
        }

        for (GSwaggerRedirectProperties.GSwaggerRedirectRule rule : rules) {
            path = path.replace(rule.getFrom(), rule.getTo());
        }
        return path;
    }
}