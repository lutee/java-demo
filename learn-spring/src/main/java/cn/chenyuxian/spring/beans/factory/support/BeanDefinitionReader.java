package cn.chenyuxian.spring.beans.factory.support;

import cn.chenyuxian.spring.beans.BeansException;
import cn.chenyuxian.spring.core.io.Resource;
import cn.chenyuxian.spring.core.io.ResourceLoader;

public interface BeanDefinitionReader {
	
	BeanDefinitionRegistry getRegistry();
	
	ResourceLoader getResourceLoader();
	
	void loadBeanDefinitions(Resource resource) throws BeansException;
	
	void loadBeanDefinitions(Resource...resources) throws BeansException;
	
	void loadBeanDefinitions(String location) throws BeansException;
	
	void loadBeanDefinitions(String...locations) throws BeansException;
}
