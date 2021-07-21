package cn.chenyuxian.spring.beans.factory.config;

import cn.chenyuxian.spring.beans.factory.HierarchicalBeanFactory;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry{
	
	String SCOPE_SINGLETON = "singleton";
	
	String SCOPE_PROTOTYPE = "prototype";
	
	void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
	
	void destroySingletons();
}
