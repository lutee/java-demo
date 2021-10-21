package learn.spring.beans.factory.support;

import learn.spring.beans.BeansException;
import learn.spring.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {
	
	void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
	
	BeanDefinition getBeanDefinition(String beanName) throws BeansException;
	
	boolean containsBeanDefinition(String beanName);
	
	String[] getBeanDefinitionNames();
}
