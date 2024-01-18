package com.gswagger.utils;

import com.gswagger.properties.MatchnowSwaggerProperties;
import com.gswagger.properties.MatchnowSwaggerRedirectProperties;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
public class DefaultRedirectPathReplacer implements MatchnowSwaggerRedirectPathReplacer {
    private final MatchnowSwaggerProperties properties;

    @Override
    public String replace(String path) {
        MatchnowSwaggerRedirectProperties redirectRules = properties.getRedirectRules();
        if (Objects.nonNull(redirectRules)) {
            path = replaceByRules(path, redirectRules);
        }
        return path;
    }

    private static String replaceByRules(String path, MatchnowSwaggerRedirectProperties redirectRules) {
        List<MatchnowSwaggerRedirectProperties.MatchnowSwaggerRedirectRule> rules = new ArrayList<>();
        if (!isEmpty(redirectRules.getExternal())) {
            rules.addAll(redirectRules.getExternal());
        }

        if (!isEmpty(redirectRules.getInternal())) {
            rules.addAll(redirectRules.getInternal());
        }

        for (MatchnowSwaggerRedirectProperties.MatchnowSwaggerRedirectRule rule : rules) {
            path = path.replace(rule.getFrom(), rule.getTo());
        }
        return path;
    }
}