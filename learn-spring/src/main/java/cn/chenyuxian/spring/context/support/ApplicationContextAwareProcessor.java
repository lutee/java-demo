package cn.chenyuxian.spring.context.support;

import cn.chenyuxian.spring.beans.BeansException;
import cn.chenyuxian.spring.beans.factory.config.BeanPostProcessor;
import cn.chenyuxian.spring.context.ApplicationContext;
import cn.chenyuxian.spring.context.ApplicationContextAware;

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
