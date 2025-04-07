package com.matchnowswagger.utils;

import com.matchnowswagger.properties.MatchnowSwaggerGroup;
import org.springdoc.core.GroupedOpenApi;

public interface MatchnowSwaggerGroupGenerator {
    GroupedOpenApi generateGroup(MatchnowSwaggerGroup group);
}
