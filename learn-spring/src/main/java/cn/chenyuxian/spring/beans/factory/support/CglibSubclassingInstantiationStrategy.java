package cn.chenyuxian.spring.beans.factory.support;

import java.lang.reflect.Constructor;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.NoOp;

import cn.chenyuxian.spring.beans.BeansException;
import cn.chenyuxian.spring.beans.factory.config.BeanDefinition;

public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy{

	@Override
	public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor constructor, Object[] args)
			throws BeansException {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(beanDefinition.getBeanClass());
		enhancer.setCallback(new NoOp() {
			@Override
			public int hashCode() {
				return super.hashCode();
			}
		});
		if(constructor == null) return enhancer.create();
		return enhancer.create(constructor.getParameterTypes(), args);
	}

}
