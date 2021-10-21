package learn.spring.beans.factory.support;

import learn.spring.beans.BeansException;
import learn.spring.core.io.Resource;
import learn.spring.core.io.ResourceLoader;

public interface BeanDefinitionReader {
	
	BeanDefinitionRegistry getRegistry();
	
	ResourceLoader getResourceLoader();
	
	void loadBeanDefinitions(Resource resource) throws BeansException;
	
	void loadBeanDefinitions(Resource...resources) throws BeansException;
	
	void loadBeanDefinitions(String location) throws BeansException;
	
	void loadBeanDefinitions(String...locations) throws BeansException;
}
