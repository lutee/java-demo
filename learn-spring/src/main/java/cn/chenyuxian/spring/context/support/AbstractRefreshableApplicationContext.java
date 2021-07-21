package cn.chenyuxian.spring.context.support;

import cn.chenyuxian.spring.beans.BeansException;
import cn.chenyuxian.spring.beans.factory.ConfigurableListableBeanFactory;
import cn.chenyuxian.spring.beans.factory.support.DefaultListableBeanFactory;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{
	
	private DefaultListableBeanFactory beanFactory;
	
	@Override
	protected void refreshBeanFactory() throws BeansException {
		DefaultListableBeanFactory beanFactory = createBeanFactory();
		loadBeanDefinitions(beanFactory);
		this.beanFactory = beanFactory;
	}
	
	private DefaultListableBeanFactory createBeanFactory() {
		return new DefaultListableBeanFactory();
	}
	
	protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);
	
	@Override
	protected ConfigurableListableBeanFactory getBeanFactory() {
		return beanFactory;
	}
}
