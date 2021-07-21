package cn.chenyuxian.spring.beans.factory.support;

import java.lang.reflect.Constructor;

import cn.chenyuxian.spring.beans.BeansException;
import cn.chenyuxian.spring.beans.factory.config.BeanDefinition;

public interface InstantiationStrategy {
	
	Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor constructor, Object[] args) throws BeansException;
}
