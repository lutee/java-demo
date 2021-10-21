package learn.spring.beans.factory;


import learn.spring.beans.BeansException;
import learn.spring.beans.factory.config.AutowireCapableBeanFactory;
import learn.spring.beans.factory.config.BeanDefinition;
import learn.spring.beans.factory.config.BeanPostProcessor;
import learn.spring.beans.factory.config.ConfigurableBeanFactory;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory{
	
	BeanDefinition getBeanDefinition(String beanName) throws BeansException;
	
	void preInstantiateSingletons() throws BeansException;
	
	void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
