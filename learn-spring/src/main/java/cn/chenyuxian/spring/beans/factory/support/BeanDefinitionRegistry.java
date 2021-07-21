package cn.chenyuxian.spring.beans.factory.support;

import cn.chenyuxian.spring.beans.BeansException;
import cn.chenyuxian.spring.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {
	
	void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
	
	BeanDefinition getBeanDefinition(String beanName) throws BeansException;
	
	boolean containsBeanDefinition(String beanName);
	
	String[] getBeanDefinitionNames();
}
