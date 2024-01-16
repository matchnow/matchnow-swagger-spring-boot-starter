package com.gswagger.config;

import com.gswagger.GSwaggerConstants;
import com.gswagger.filter.GSwaggerCorsFilter;
import com.gswagger.properties.GSwaggerProperties;
import com.gswagger.utils.DefaultGroupGenerator;
import com.gswagger.utils.DefaultRedirectPathReplacer;
import com.gswagger.utils.GSwaggerGroupGenerator;
import com.gswagger.utils.GSwaggerRedirectPathReplacer;
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
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import static com.gswagger.GSwaggerConstants.*;


@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty({GSwaggerConstants.GSWAGGER_ENABLED, Constants.SPRINGDOC_ENABLED, Constants.SPRINGDOC_SWAGGER_UI_ENABLED})
public class GSwaggerAutoConfiguration {

    @Bean
    @Primary
    public SwaggerUiConfigParameters swaggerCustomUiConfigParameters(
            SwaggerUiConfigProperties swaggerUiConfig,
            ApplicationContext applicationContext,
            @Value(REDIRECT_PATH_REPLACER) String pathReplacerName) {
        return new GSwaggerUiConfigParameters(swaggerUiConfig, applicationContext.getBean(pathReplacerName, GSwaggerRedirectPathReplacer.class));
    }

    @Bean
    public GSwaggerProperties swaggerCustomProperties(Environment environment) {
        return Binder.get(environment)
                .bind(GSWAGGER_PROPERTIES, GSwaggerProperties.class)
                .get();
    }

    @Bean
    public GSwaggerGroupGenerator defaultGroupGenerator(
            GSwaggerProperties properties,
            ApplicationContext applicationContext,
            @Value(REDIRECT_PATH_REPLACER) String pathReplacerName
    ) {
        return new DefaultGroupGenerator(properties, applicationContext.getBean(pathReplacerName, GSwaggerRedirectPathReplacer.class));
    }

    @Bean
    public GSwaggerGroupRegisterer swaggerCustomInitializer(
            GSwaggerProperties swaggerCustomProperties,
            GSwaggerGroupGenerator groupGenerator) {
        return new GSwaggerGroupRegisterer(swaggerCustomProperties, groupGenerator);
    }

    @Bean
    public GSwaggerRedirectPathReplacer defaultRedirectPathReplacer() {
        return new DefaultRedirectPathReplacer();
    }

    /**
     * 다음 Configuration 클래스는 GroupedOpenApi 빈들을 찾아 스웨거 UI에 표시될 수 있도록 해준다.
     * 이 클래스를 빈으로 등록하기 위한 조건을 만족하기 위해 비어있는 GroupedOpenApi 를 빈으로 등록해준다.
     *
     * @see MultipleOpenApiSupportCondition
     * @see MultipleOpenApiSupportConfiguration
     */
    @Bean
    public GroupedOpenApi groupedOpenApi(GSwaggerProperties properties, GSwaggerGroupGenerator groupGenerator) {
        if (CollectionUtils.isEmpty(properties.getGroups())) {
            throw new IllegalStateException("gswagger's groups must be present");
        }
        return groupGenerator.generateGroup(properties.getGroups().get(0));
    }

    /**
     * NOTE: <b>모든 요청</b>에 CORS 를 허용한다.
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ConditionalOnProperty(GSWAGGER_CORS_ENABLED)
    public OncePerRequestFilter gSwaggerCorsFilter() {
        return new GSwaggerCorsFilter();
    }

    /**
     * SWAGGER 관련 모든 요청을 허용한다.
     */
    @Bean
    @ConditionalOnProperty(GSWAGGER_SECURITY_CONFIG_ENABLED)
    public WebSecurityCustomizer webSecurityCustomizer(
            @Value(Constants.API_DOCS_URL) String apiDocsUrl,
            @Value(Constants.SWAGGER_UI_PATH) String swaggerUiPath
    ) {
        return (web) -> web.ignoring()
                .antMatchers(apiDocsUrl)
                .antMatchers(apiDocsUrl + "/**")
                .antMatchers(swaggerUiPath)
                .antMatchers(swaggerUiPath + "/**");
    }
}
