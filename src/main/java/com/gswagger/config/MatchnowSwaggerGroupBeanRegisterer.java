package com.gswagger.config;

import com.gswagger.properties.MatchnowSwaggerGroup;
import com.gswagger.properties.MatchnowSwaggerProperties;
import com.gswagger.utils.MatchnowSwaggerGroupGenerator;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

@RequiredArgsConstructor
public class MatchnowSwaggerGroupBeanRegisterer implements BeanFactoryAware {
    private final MatchnowSwaggerProperties properties;
    private final MatchnowSwaggerGroupGenerator groupGenerator;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        ConfigurableListableBeanFactory factory = (ConfigurableListableBeanFactory) beanFactory;
        // 첫번째 그룹은 이미 GSwaggerAutoConfiguration에서 등록했기 때문에 두번째 그룹부터 등록한다.
        for (int i = 1; i < properties.getGroups().size(); i++) {
            MatchnowSwaggerGroup group = properties.getGroups().get(i);
            String beanName = GroupedOpenApi.class.getName() + i;
            factory.registerSingleton(beanName, groupGenerator.generateGroup(group));
        }
    }


}
