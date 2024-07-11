package com.gswagger;

public class MatchnowSwaggerConstants {
    public static final String MATCHNOW_SWAGGER_ENABLED = "springdoc.matchnow-swagger.enabled";
    public static final String MATCHNOW_SWAGGER_CORS_ENABLED = "springdoc.matchnow-swagger.enable-cors";
    public static final String MATCHNOW_SWAGGER_PROPERTIES = "springdoc.matchnow-swagger";
    public static final String REDIRECT_PATH_REPLACER = "${springdoc.matchnow-swagger.redirect-path-replacer:defaultRedirectPathReplacer}";
    public static final String MATHCNOW_SWAGGER_PROPERTY_URL = "${springdoc.matchnow-swagger.property-url:/swagger-ui/properties}";
    public static final String MODEL_RESOVER_OBJECT_MAPPER = "${springdoc.matchnow-swagger.object-mapper:jacksonObjectMapper}";
}
