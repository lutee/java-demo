package learn.spring.beans.factory.support;

import java.lang.reflect.Constructor;

import learn.spring.beans.BeansException;
import learn.spring.beans.factory.config.BeanDefinition;

public interface InstantiationStrategy {
	
	Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor constructor, Object[] args) throws BeansException;
}
