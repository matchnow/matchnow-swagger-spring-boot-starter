package com.matchnowswagger.utils;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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
    }

    // Schema 다국어 처리
    public static Schema<?> resolve(AnnotatedType type, ModelConverterContext context, Iterator<ModelConverter> chain, MessageSource messageSource) {
        Locale locale = LocaleContextHolder.getLocale();

        if (chain.hasNext()) {
            Schema<?> schema = chain.next().resolve(type, context, chain);
            if (schema != null ) {
                schema.setDescription(resolveMessage(schema.getDescription(), locale, messageSource));
                schema.setName(resolveMessage(schema.getName(), locale, messageSource));
                schema.setTitle(resolveMessage(schema.getTitle(), locale, messageSource));

                if (schema.getProperties() != null) {
                    schema.getProperties().forEach((key, value) -> {
                        resolve(type, context, chain, messageSource);
                    });
                }

            }
            return schema;
        }
        return null;
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
