package com.gswagger.utils;

import com.gswagger.properties.GSwaggerGlobalHeaders;
import com.gswagger.properties.GSwaggerGroup;
import com.gswagger.properties.GSwaggerProperties;
import com.gswagger.properties.GSwaggerServers;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DefaultGroupGenerator implements GSwaggerGroupGenerator {
    private final GSwaggerProperties properties;
    private final GSwaggerRedirectPathReplacer pathReplacer;

    @Override
    public GroupedOpenApi generateGroup(GSwaggerGroup group) {
        return GroupedOpenApi.builder()
                .group(group.getTitle())
                .pathsToMatch(group.getPathPattern())
                .addOpenApiCustomiser(new CustomOpenApiCustomsier(group))
                .build();
    }

    private SecurityScheme globalHeader(String name, String example) {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .description("Example: " + example)
                .in(SecurityScheme.In.HEADER).name(name);
    }

    private void setSecuritySchemes(OpenAPI openApi, List<GSwaggerGlobalHeaders.GSwaggerHeader> headers) {
        headers.forEach(header -> openApi.getComponents()
                .addSecuritySchemes(header.getName(), globalHeader(header.getName(), header.getExample())));
    }

    private void setSecurityRequirement(OpenAPI openApi, List<GSwaggerGlobalHeaders.GSwaggerHeader> headers) {
        SecurityRequirement securityRequirement = new SecurityRequirement();
        headers.forEach(header -> securityRequirement.addList(header.getName()));
        openApi.addSecurityItem(securityRequirement);
    }


    private List<Server> createServers(List<GSwaggerServers.GSwaggerServer> servers) {
        return servers.stream().map(server -> new Server().description(server.getName()).url(server.getUrl())).collect(Collectors.toList());
    }

    /**
     * Swagger 문서에 노출될 API 들의 path를 수정한다.
     */
    private Paths replacePaths(OpenAPI openApi) {
        final Paths paths = openApi.getPaths();
        final Paths newPaths = new Paths();
        for (Map.Entry<String, PathItem> entry : paths.entrySet()) {
            String newPath = pathReplacer.replace(entry.getKey());
            newPaths.addPathItem(newPath, entry.getValue());
        }
        return newPaths;
    }

    @RequiredArgsConstructor
    private class CustomOpenApiCustomsier implements OpenApiCustomiser {
        private final GSwaggerGroup group;

        @Override
        public void customise(OpenAPI openApi) {
            openApi
                    .paths(replacePaths(openApi))
                    .info(new Info()
                            .title(group.getTitle())
                            .description(group.getDescription())
                            .version(group.getVersion()))
                    .servers(createServers(
                            group.isInternal() ?
                                    properties.getServers().getInternal() :
                                    properties.getServers().getExternal()));


            List<GSwaggerGlobalHeaders.GSwaggerHeader> headers = group.isInternal() ?
                    properties.getGlobalHeaders().getInternal() :
                    properties.getGlobalHeaders().getExternal();

            setSecuritySchemes(openApi, headers);
            setSecurityRequirement(openApi, headers);
        }
    }
}
