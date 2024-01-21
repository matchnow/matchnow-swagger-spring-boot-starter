package com.gswagger.config;

import com.gswagger.MatchnowSwaggerConstants;
import com.gswagger.controller.MatchnowSwaggerPropertyController;
import com.gswagger.filter.MatchnowSwaggerCorsFilter;
import com.gswagger.properties.MatchnowSwaggerProperties;
import com.gswagger.utils.DefaultGroupGenerator;
import com.gswagger.utils.DefaultRedirectPathReplacer;
import com.gswagger.utils.MatchnowSwaggerGroupGenerator;
import com.gswagger.utils.MatchnowSwaggerRedirectPathReplacer;
import org.springdoc.core.*;
import org.springdoc.webmvc.core.MultipleOpenApiSupportConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import static com.gswagger.MatchnowSwaggerConstants.*;
import static org.springdoc.core.Constants.API_DOCS_URL;
import static org.springdoc.core.Constants.SWAGGER_UI_PATH;


@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty({MatchnowSwaggerConstants.MATCHNOW_SWAGGER_ENABLED, Constants.SPRINGDOC_ENABLED, Constants.SPRINGDOC_SWAGGER_UI_ENABLED})
public class MatchnowSwaggerAutoConfiguration {

    @Bean
    @Primary
    public SwaggerUiConfigParameters swaggerCustomUiConfigParameters(
            SwaggerUiConfigProperties swaggerUiConfig,
            ApplicationContext applicationContext,
            @Value(REDIRECT_PATH_REPLACER) String pathReplacerName) {
        return new MatchnowSwaggerUiConfigParameters(swaggerUiConfig, applicationContext.getBean(pathReplacerName, MatchnowSwaggerRedirectPathReplacer.class));
    }

    @Bean
    public MatchnowSwaggerProperties swaggerCustomProperties(Environment environment) {
        return Binder.get(environment)
                .bind(MATCHNOW_SWAGGER_PROPERTIES, MatchnowSwaggerProperties.class)
                .get();
    }

    @Bean
    public MatchnowSwaggerGroupGenerator defaultGroupGenerator(
            MatchnowSwaggerProperties properties,
            ApplicationContext applicationContext,
            @Value(REDIRECT_PATH_REPLACER) String pathReplacerName
    ) {
        return new DefaultGroupGenerator(properties, applicationContext.getBean(pathReplacerName, MatchnowSwaggerRedirectPathReplacer.class));
    }

    @Bean
    public MatchnowSwaggerGroupBeanRegisterer swaggerCustomInitializer(
            MatchnowSwaggerProperties swaggerCustomProperties,
            MatchnowSwaggerGroupGenerator groupGenerator) {
        return new MatchnowSwaggerGroupBeanRegisterer(swaggerCustomProperties, groupGenerator);
    }

    @Bean
    public MatchnowSwaggerRedirectPathReplacer defaultRedirectPathReplacer(MatchnowSwaggerProperties properties) {
        return new DefaultRedirectPathReplacer(properties);
    }

    /**
     * 다음 Configuration 클래스는 GroupedOpenApi 빈들을 찾아 스웨거 UI에 표시될 수 있도록 해준다.
     * 이 클래스를 빈으로 등록하기 위한 조건을 만족하기 위해 비어있는 GroupedOpenApi 를 빈으로 등록해준다.
     *
     * @see MultipleOpenApiSupportCondition
     * @see MultipleOpenApiSupportConfiguration
     */
    @Bean
    public GroupedOpenApi groupedOpenApi(MatchnowSwaggerProperties properties, MatchnowSwaggerGroupGenerator groupGenerator) {
        if (CollectionUtils.isEmpty(properties.getGroups())) {
            throw new IllegalStateException("matchnow-swagger's groups must be present");
        }
        return groupGenerator.generateGroup(properties.getGroups().get(0));
    }

    /**
     * NOTE: <b>모든 요청</b>에 CORS 를 허용한다.
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ConditionalOnProperty(MATCHNOW_SWAGGER_CORS_ENABLED)
    public OncePerRequestFilter gSwaggerCorsFilter() {
        return new MatchnowSwaggerCorsFilter();
    }

    @Bean
    public MatchnowSwaggerPropertyController matchnowSwaggerPropertyController(@Value(SWAGGER_UI_PATH) String swaggerUiPath, @Value(API_DOCS_URL) String apiDocPath) {
        return new MatchnowSwaggerPropertyController(swaggerUiPath, apiDocPath);
    }
}
