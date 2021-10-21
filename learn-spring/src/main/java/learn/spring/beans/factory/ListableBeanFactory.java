package learn.spring.beans.factory;

import java.util.Map;

import learn.spring.beans.BeansException;

public interface ListableBeanFactory extends BeanFactory{
	
	<T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;
	
	String[] getBeanDefinitionNames();
}
