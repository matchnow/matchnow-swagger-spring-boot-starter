package com.matchnowswagger.utils;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.links.Link;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class I18nUtils {
    //  Info, Tag, Operation 다국어 처리
    public static void applyI18n(OpenAPI openApi, MessageSource messageSource) {
        Locale locale = LocaleContextHolder.getLocale();

        // Info
        if (openApi.getInfo() != null) {
            openApi.getInfo().setTitle(resolveMessage(openApi.getInfo().getTitle(), locale, messageSource));
            openApi.getInfo().setDescription(resolveMessage(openApi.getInfo().getDescription(), locale, messageSource));
        }

        // Tags
        List<Tag> tags = openApi.getTags();
        if (tags != null) {
            for (Tag tag : tags) {
                tag.setName(resolveMessage(tag.getName(), locale, messageSource));
                tag.setDescription(resolveMessage(tag.getDescription(), locale, messageSource));
            }
        }

        // Operations
        openApi.getPaths().forEach((path, pathItem) -> {
            pathItem.readOperations().forEach(operation -> {
                operation.setSummary(resolveMessage(operation.getSummary(), locale, messageSource));
                operation.setDescription(resolveMessage(operation.getDescription(), locale, messageSource));

                if (operation.getTags() != null) {
                    List<String> resolvedTags = operation.getTags().stream()
                            .map(tag -> resolveMessage(tag, locale, messageSource))
                            .collect(Collectors.toList());
                    operation.setTags(resolvedTags);
                }

                if (operation.getParameters() != null) {
                    for (Parameter parameter : operation.getParameters()) {
                        parameter.setName(resolveMessage(parameter.getName(), locale, messageSource));
                        parameter.setDescription(resolveMessage(parameter.getDescription(), locale, messageSource));
                    }
                }
            });
        });

        // Components
        Components components = openApi.getComponents();
        if (components != null) {
            Map<String, Schema> schemas = components.getSchemas();
            if (schemas != null) {
                schemas.forEach((key, schema) -> {
                    resolveSchema(schema, locale, messageSource);
                });
            }

            Map<String, ApiResponse> responses = components.getResponses();
            if (responses != null) {
                responses.forEach((key, response) -> {
                    response.setDescription(resolveMessage(response.getDescription(), locale, messageSource));
                });
            }

            Map<String, Parameter> parameters = components.getParameters();
            if (parameters != null) {
                parameters.forEach((key, parameter) -> {
                    parameter.setName(resolveMessage(parameter.getName(), locale, messageSource));
                    parameter.setDescription(resolveMessage(parameter.getDescription(), locale, messageSource));
                });
            }

            Map<String, Example> examples = components.getExamples();
            if (examples != null ) {
                examples.forEach((key, example) -> {
                    example.setSummary(resolveMessage(example.getSummary(), locale, messageSource));
                    example.setDescription(resolveMessage(example.getDescription(), locale, messageSource));
                });
            }

            Map<String, RequestBody> requestBodies = components.getRequestBodies();
            if (requestBodies != null) {
                requestBodies.forEach((key, requestBody) -> {
                    requestBody.setDescription(resolveMessage(requestBody.getDescription(), locale, messageSource));
                });
            }

            Map<String, Header> headers = components.getHeaders();
            if (headers != null) {
                headers.forEach((key, header) -> {
                    header.setDescription(resolveMessage(header.getDescription(), locale, messageSource));
                    resolveSchema(header.getSchema(), locale, messageSource);
                });
            }

            Map<String, SecurityScheme> securitySchemes = components.getSecuritySchemes();
            if (securitySchemes != null) {
                securitySchemes.forEach((key, securityScheme) -> {
                    securityScheme.setName(resolveMessage(securityScheme.getName(), locale, messageSource));
                    securityScheme.setDescription(resolveMessage(securityScheme.getDescription(), locale, messageSource));
                });
            }

            Map<String, Link> links = components.getLinks();
            if (links != null) {
                links.forEach((key, link) -> {
                    link.setDescription(resolveMessage(link.getDescription(), locale, messageSource));
                });
            }

        }
    }

    // Schema 다국어 처리
    public static Schema<?> resolve(AnnotatedType type, ModelConverterContext context, Iterator<ModelConverter> chain, MessageSource messageSource) {
        Locale locale = LocaleContextHolder.getLocale();

        if (chain.hasNext()) {
            Schema<?> schema = chain.next().resolve(type, context, chain);
            return resolveSchema(schema, locale, messageSource);
        }

        return null;
    }

    private static Schema<?> resolveSchema(Schema<?> schema, Locale locale, MessageSource messageSource) {
        if (schema == null) return null;

        schema.setDescription(resolveMessage(schema.getDescription(), locale, messageSource));
        schema.setName(resolveMessage(schema.getName(), locale, messageSource));
        schema.setTitle(resolveMessage(schema.getTitle(), locale, messageSource));

        if (schema.getProperties() != null) {
            schema.getProperties().forEach((key, value) -> {
                resolveSchema(value, locale, messageSource);
            });
        }

        return schema;
    }

    private static String resolveMessage(String value, Locale locale, MessageSource messageSource) {
        if (StringUtils.isBlank(value)) return value;

        try {
            return messageSource.getMessage(value, null, locale);
        } catch (Exception e) {
            return value;
        }
    }
}
