package cn.chenyuxian.spring.common;

import learn.spring.beans.BeansException;
import learn.spring.beans.PropertyValue;
import learn.spring.beans.PropertyValues;
import learn.spring.beans.factory.ConfigurableListableBeanFactory;
import learn.spring.beans.factory.config.BeanDefinition;
import learn.spring.beans.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor{

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
		PropertyValues propertyValues = beanDefinition.getPropertyValues();
		propertyValues.addPropertyValue(new PropertyValue("company", "改为:字节跳动"));
	}

	
}
