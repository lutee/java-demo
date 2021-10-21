package learn.spring.beans.factory;

import learn.spring.beans.BeansException;

public interface BeanFactoryAware extends Aware{

	void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
