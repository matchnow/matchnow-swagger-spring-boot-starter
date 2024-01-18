package com.gswagger.config;

import com.gswagger.utils.MatchnowSwaggerRedirectPathReplacer;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Set;

public class MatchnowSwaggerUiConfigParameters extends SwaggerUiConfigParameters {
    private final MatchnowSwaggerRedirectPathReplacer pathReplacer;

    /**
     * Instantiates a new Swagger ui config parameters.
     *
     * @param swaggerUiConfig the swagger ui config
     */
    public MatchnowSwaggerUiConfigParameters(SwaggerUiConfigProperties swaggerUiConfig,
                                             MatchnowSwaggerRedirectPathReplacer pathReplacer) {
        super(swaggerUiConfig);
        this.pathReplacer = pathReplacer;
    }

    @Override
    public Map<String, Object> getConfigParameters() {
        Map<String, Object> configParameters = super.getConfigParameters();
        String configUrl = (String) configParameters.get(CONFIG_URL_PROPERTY);
        if (StringUtils.isNotBlank(configUrl)) {
            configParameters.put(CONFIG_URL_PROPERTY, pathReplacer.replace(configUrl));
        }
        Set<SwaggerUrl> urls = (Set<SwaggerUrl>) configParameters.get(URLS_PROPERTY);
        if (!CollectionUtils.isEmpty(urls)) {
            urls.forEach(it -> it.setUrl(pathReplacer.replace(it.getUrl())));
        }
        return configParameters;
    }

    @Override
    public String getUiRootPath() {
        String uiRootPath = super.getUiRootPath();
        if (StringUtils.isNotBlank(uiRootPath)) {
            return pathReplacer.replace(uiRootPath);
        }
        return uiRootPath;
    }
}
