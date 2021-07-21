package cn.chenyuxian.spring.beans.factory.config;

import cn.chenyuxian.spring.beans.BeansException;
import cn.chenyuxian.spring.beans.factory.ConfigurableListableBeanFactory;

public interface BeanFactoryPostProcessor {
	
	/**
	 * 在所有的beandefinition加载完成后，实例化bean对象之前，提供修改beandefinition属性的机制
	 * @param beanFactory
	 * @throws BeansException
	 */
	void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
