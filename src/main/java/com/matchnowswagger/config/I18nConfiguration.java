package com.matchnowswagger.config;

import com.matchnowswagger.utils.I18nUtils;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverters;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration(proxyBeanMethods = false)
public class I18nConfiguration {

    @Bean
    public ModelConverter i18nModelConverter(MessageSource messageSource) {
        ModelConverter modelConverter = (annotatedType, modelConverterContext, iterator)
                -> I18nUtils.resolve(annotatedType, modelConverterContext, iterator, messageSource);

        ModelConverters.getInstance().addConverter(modelConverter);

        return modelConverter;
    }
}
