package cn.chenyuxian.spring.beans.factory.support;

import java.lang.reflect.Method;

import cn.chenyuxian.spring.beans.BeansException;
import cn.chenyuxian.spring.beans.factory.DisposableBean;
import cn.chenyuxian.spring.beans.factory.config.BeanDefinition;
import cn.hutool.core.util.StrUtil;

public class DisposableBeanAdapter implements DisposableBean{

	private final Object bean;
	
	private final String beanName;
	
	private String destroyMethodName;
	
	public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
		this.bean = bean;
		this.beanName = beanName;
		this.destroyMethodName = beanDefinition.getDestroyMethodName();
	}

	@Override
	public void destroy() throws Exception {
		if(bean instanceof DisposableBean) {
			((DisposableBean) bean).destroy();
		}
		
		if(StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))) {
			Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
			if(null == destroyMethod) {
				throw new BeansException("Couldn't find a destroy method named" + destroyMethodName + "on bean with me" + beanName + "");
			}
			destroyMethod.invoke(bean);
		}
	}

}
