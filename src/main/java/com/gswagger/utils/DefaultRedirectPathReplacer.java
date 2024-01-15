package com.gswagger.utils;

public class DefaultRedirectPathReplacer implements GSwaggerRedirectPathReplacer {
    @Override
    public String replace(String path) {
        return path;
    }
}