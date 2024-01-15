package com.gswagger.config;

import com.gswagger.properties.GSwaggerGroup;
import com.gswagger.properties.GSwaggerProperties;
import com.gswagger.utils.GSwaggerGroupGenerator;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

@RequiredArgsConstructor
public class GSwaggerGroupRegisterer implements BeanFactoryAware {
    private final GSwaggerProperties properties;
    private final GSwaggerGroupGenerator groupGenerator;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        ConfigurableListableBeanFactory factory = (ConfigurableListableBeanFactory) beanFactory;
        // 첫번째 그룹은 이미 GSwaggerAutoConfiguration에서 등록했기 때문에 두번째 그룹부터 등록한다.
        for (int i = 1; i < properties.getGroups().size(); i++) {
            GSwaggerGroup group = properties.getGroups().get(i);
            String beanName = GroupedOpenApi.class.getName() + i;
            factory.registerSingleton(beanName, groupGenerator.generateGroup(group));
        }
    }


}
