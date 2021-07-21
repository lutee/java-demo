package cn.chenyuxian.spring.beans.factory;

import java.util.Map;

import cn.chenyuxian.spring.beans.BeansException;

public interface ListableBeanFactory extends BeanFactory{
	
	<T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;
	
	String[] getBeanDefinitionNames();
}
