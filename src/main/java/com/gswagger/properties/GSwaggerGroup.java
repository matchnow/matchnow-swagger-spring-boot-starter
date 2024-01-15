package com.gswagger.properties;


import com.gswagger.GSwaggerPathType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GSwaggerGroup {
    private String title;
    private String description;
    private GSwaggerPathType pathType;
    private String pathPattern;
    private String version;

    public boolean isInternal() {
        return pathType.equals(GSwaggerPathType.INTERNAL);
    }
}
