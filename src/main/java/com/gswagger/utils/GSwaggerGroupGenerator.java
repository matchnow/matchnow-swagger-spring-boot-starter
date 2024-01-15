package com.gswagger.utils;

import com.gswagger.properties.GSwaggerGroup;
import org.springdoc.core.GroupedOpenApi;

public interface GSwaggerGroupGenerator {
    GroupedOpenApi generateGroup(GSwaggerGroup group);
}
