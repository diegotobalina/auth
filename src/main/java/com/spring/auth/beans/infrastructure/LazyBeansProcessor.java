package com.spring.auth.beans.infrastructure;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!dev")
public class LazyBeansProcessor implements BeanFactoryPostProcessor {
  @Override
  public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory)
      throws BeansException {
    for (final String beanName : beanFactory.getBeanDefinitionNames()) {
      beanFactory.getBeanDefinition(beanName).setLazyInit(true);
    }
  }
}
