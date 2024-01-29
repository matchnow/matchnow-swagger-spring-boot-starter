package com.gswagger.utils;

import com.gswagger.properties.MatchnowSwaggerGlobalHeaders;
import com.gswagger.properties.MatchnowSwaggerGroup;
import com.gswagger.properties.MatchnowSwaggerProperties;
import com.gswagger.properties.MatchnowSwaggerServers;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DefaultGroupGenerator implements MatchnowSwaggerGroupGenerator {
    private final MatchnowSwaggerProperties properties;
    private final MatchnowSwaggerRedirectPathReplacer pathReplacer;

    @Override
    public GroupedOpenApi generateGroup(MatchnowSwaggerGroup group) {
        String[] pathsToMatch = StringUtils.isNotBlank(group.getPathPattern()) ?
                new String[]{group.getPathPattern()} :
                group.getIncludePathPatterns().toArray(new String[0]);

        GroupedOpenApi.Builder builder = GroupedOpenApi.builder()
                .group(group.getTitle())
                .pathsToMatch(pathsToMatch)
                .addOpenApiCustomiser(new CustomOpenApiCustomsier(group));

        if (!CollectionUtils.isEmpty(group.getExcludePathPatterns())) {
            builder.pathsToExclude(group.getExcludePathPatterns().toArray(new String[]{}));
        }
        return builder.build();
    }

    private SecurityScheme globalHeader(String name, String example) {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .description("Example: " + example)
                .in(SecurityScheme.In.HEADER).name(name);
    }

    private void setSecuritySchemes(OpenAPI openApi, List<MatchnowSwaggerGlobalHeaders.MatchnowSwaggerHeader> headers) {
        headers.forEach(header -> openApi.getComponents()
                .addSecuritySchemes(header.getName(), globalHeader(header.getName(), header.getExample())));
    }

    private void setSecurityRequirement(OpenAPI openApi, List<MatchnowSwaggerGlobalHeaders.MatchnowSwaggerHeader> headers) {
        SecurityRequirement securityRequirement = new SecurityRequirement();
        headers.forEach(header -> securityRequirement.addList(header.getName()));
        openApi.addSecurityItem(securityRequirement);
    }


    private List<Server> createServers(List<MatchnowSwaggerServers.MatchnowSwaggerServer> servers) {
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
        private final MatchnowSwaggerGroup group;

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


            setGlobalHeaders(openApi);
        }

        private void setGlobalHeaders(OpenAPI openApi) {
            if (Objects.nonNull(properties.getGlobalHeaders())) {
                List<MatchnowSwaggerGlobalHeaders.MatchnowSwaggerHeader> headers =
                        group.isInternal() ?
                                properties.getGlobalHeaders().getInternal() :
                                properties.getGlobalHeaders().getExternal();

                if (!CollectionUtils.isEmpty(headers)) {
                    setSecuritySchemes(openApi, headers);
                    setSecurityRequirement(openApi, headers);
                }
            }
        }
    }
}
