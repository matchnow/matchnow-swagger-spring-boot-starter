package com.gswagger.properties;


import com.gswagger.MatchnowSwaggerPathType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchnowSwaggerGroup {
    private String title;
    private String description;
    private MatchnowSwaggerPathType pathType;
    private String pathPattern;
    private String version;

    public boolean isInternal() {
        return pathType.equals(MatchnowSwaggerPathType.INTERNAL);
    }
}
