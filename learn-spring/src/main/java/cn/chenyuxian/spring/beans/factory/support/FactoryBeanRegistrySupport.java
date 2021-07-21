package cn.chenyuxian.spring.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.chenyuxian.spring.beans.BeansException;
import cn.chenyuxian.spring.beans.factory.FactoryBean;

public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

	private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

	protected Object getCachedObjectForFactoryBean(String beanName) {
		Object object = this.factoryBeanObjectCache.get(beanName);
		return object != NULL_OBJECT ? object : null;
	}

	protected Object getObjectFromFactoryBean(FactoryBean factory, String beanName) {
		if (factory.isSingleton()) {
			Object object = this.factoryBeanObjectCache.get(beanName);
			if (object == null) {
				object = doGetObjectFromFactoryBean(factory, beanName);
				if (object == null) {
					object = NULL_OBJECT;
				}
				this.factoryBeanObjectCache.put(beanName, object);
			}
			return object != NULL_OBJECT ? object : null;
		} else {
			return doGetObjectFromFactoryBean(factory, beanName);
		}
	}

	private Object doGetObjectFromFactoryBean(final FactoryBean factory, final String beanName) {
		try {
			return factory.getObject();
		} catch (Exception e) {
			// TODO: handle exception
			throw new BeansException("FactoryBean threw exception on object[" + beanName + "]creation", e);
		}
	}
}
