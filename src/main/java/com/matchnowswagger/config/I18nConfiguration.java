package com.matchnowswagger.config;

import com.matchnowswagger.utils.I18nUtils;
import io.swagger.v3.core.converter.ModelConverters;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration(proxyBeanMethods = false)
public class I18nConfiguration {

    @Bean
    public MessageSource matchnowSwaggerMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:docs");
        messageSource.setDefaultEncoding("UTF-8");
        applyI18nSchemaConverter(messageSource);
        return messageSource;
    }

    private void applyI18nSchemaConverter(MessageSource messageSource) {
        ModelConverters.getInstance().addConverter((annotatedType, modelConverterContext, iterator)
                -> I18nUtils.resolve(annotatedType, modelConverterContext, iterator, messageSource));
    }
}
