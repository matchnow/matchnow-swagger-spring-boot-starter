package com.gswagger.utils;

import com.gswagger.properties.MatchnowSwaggerGroup;
import org.springdoc.core.GroupedOpenApi;

public interface MatchnowSwaggerGroupGenerator {
    GroupedOpenApi generateGroup(MatchnowSwaggerGroup group);
}
