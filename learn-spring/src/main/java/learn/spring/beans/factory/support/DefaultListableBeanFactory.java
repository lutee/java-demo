package learn.spring.beans.factory.support;

import java.util.HashMap;
import java.util.Map;

import learn.spring.beans.BeansException;
import learn.spring.beans.factory.ConfigurableListableBeanFactory;
import learn.spring.beans.factory.config.BeanDefinition;
import learn.spring.beans.factory.config.BeanPostProcessor;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory{
	
	private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
		beanDefinitionMap.put(beanName, beanDefinition);
	}
	
	@Override
	public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
		BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
		if(beanDefinition == null) throw new BeansException("No bean named" + beanName + " is defined");
		return beanDefinition;
	}

	@Override
	public boolean containsBeanDefinition(String beanName) {
		return beanDefinitionMap.containsKey(beanName);
	}

	@Override
	public String[] getBeanDefinitionNames() {
		return beanDefinitionMap.keySet().toArray(new String[0]);
	}
	
	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
		Map<String, T> result = new HashMap<>();
		beanDefinitionMap.forEach((beanName, beanDefinition) -> {
			Class beanClass = beanDefinition.getBeanClass();
			if(type.isAssignableFrom(beanClass)) {
				result.put(beanName, (T) getBean(beanName));
			}
		});
		return result;
	}

	@Override
	public void preInstantiateSingletons() throws BeansException {
		beanDefinitionMap.keySet().forEach(this::getBean);
	}
	
}