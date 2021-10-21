package learn.spring.context.support;

import learn.spring.beans.BeansException;
import learn.spring.beans.factory.config.BeanPostProcessor;
import learn.spring.context.ApplicationContext;
import learn.spring.context.ApplicationContextAware;

public class ApplicationContextAwareProcessor implements BeanPostProcessor{

	private final ApplicationContext applicationContext;
	
	public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if(bean instanceof ApplicationContextAware) {
			((ApplicationContextAware) bean).setApplicationContext(applicationContext);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
