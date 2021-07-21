package cn.chenyuxian.spring.beans.factory;


import cn.chenyuxian.spring.beans.BeansException;
import cn.chenyuxian.spring.beans.factory.config.AutowireCapableBeanFactory;
import cn.chenyuxian.spring.beans.factory.config.BeanDefinition;
import cn.chenyuxian.spring.beans.factory.config.BeanPostProcessor;
import cn.chenyuxian.spring.beans.factory.config.ConfigurableBeanFactory;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory{
	
	BeanDefinition getBeanDefinition(String beanName) throws BeansException;
	
	void preInstantiateSingletons() throws BeansException;
	
	void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
