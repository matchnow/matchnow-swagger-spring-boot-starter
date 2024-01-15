package com.gswagger.utils;

public class AwsRedirectPathReplacer implements GSwaggerRedirectPathReplacer {
    @Override
    public String replace(String path) {
        return path
                .replace("/matchnow-classroom/external", "/ext/matchnow-classroom")
                .replace("/matchnow-classroom/internal", "/matchnow-classroom");
    }
}
