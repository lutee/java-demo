package cn.chenyuxian.spring.beans.factory;

import cn.chenyuxian.spring.beans.BeansException;

public interface BeanFactoryAware extends Aware{

	void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
