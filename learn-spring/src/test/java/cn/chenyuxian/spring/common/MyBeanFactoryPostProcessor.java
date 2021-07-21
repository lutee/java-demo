package cn.chenyuxian.spring.common;

import cn.chenyuxian.spring.beans.BeansException;
import cn.chenyuxian.spring.beans.PropertyValue;
import cn.chenyuxian.spring.beans.PropertyValues;
import cn.chenyuxian.spring.beans.factory.ConfigurableListableBeanFactory;
import cn.chenyuxian.spring.beans.factory.config.BeanDefinition;
import cn.chenyuxian.spring.beans.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor{

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
		PropertyValues propertyValues = beanDefinition.getPropertyValues();
		propertyValues.addPropertyValue(new PropertyValue("company", "改为:字节跳动"));
	}

	
}
