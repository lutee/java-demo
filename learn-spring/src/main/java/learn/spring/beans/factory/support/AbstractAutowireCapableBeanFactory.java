package learn.spring.beans.factory.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import learn.spring.beans.BeansException;
import learn.spring.beans.PropertyValue;
import learn.spring.beans.PropertyValues;
import learn.spring.beans.factory.Aware;
import learn.spring.beans.factory.BeanClassLoaderAware;
import learn.spring.beans.factory.BeanFactoryAware;
import learn.spring.beans.factory.BeanNameAware;
import learn.spring.beans.factory.DisposableBean;
import learn.spring.beans.factory.InitializingBean;
import learn.spring.beans.factory.config.AutowireCapableBeanFactory;
import learn.spring.beans.factory.config.BeanDefinition;
import learn.spring.beans.factory.config.BeanPostProcessor;
import learn.spring.beans.factory.config.BeanReference;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory{

	private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

	@Override
	protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
		Object bean = null;
		try {
			bean = createBeanInstance(beanDefinition, beanName, args);
			
			// 给Bean填充属性
			applyPropertyValues(beanName, bean, beanDefinition);
			
			// 执行Bean的初始化方法和BeanPostProcessor的前置和后置处理方法
			bean = initializeBean(beanName, bean, beanDefinition);
		} catch (Exception e) {
			throw new BeansException("Instantiation of bean failed", e);
		}
		
		// 注册实现了DisposableBean接口的Bean对象
		registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
		
		// 判断SCOPE_SINGLETON,SCOPE_PROTOTYPE
		if(beanDefinition.isSingleton()) {
			addSingleton(beanName, bean);			
		}
		return bean;
	}

	private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
		
		if(bean instanceof Aware) {
			if(bean instanceof BeanFactoryAware) {
				((BeanFactoryAware) bean).setBeanFactory(this);
			}
			if(bean instanceof BeanClassLoaderAware) {
				((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
			}
			if(bean instanceof BeanNameAware) {
				((BeanNameAware) bean).setBeanName(beanName);
			}
		}
		
		// 执行BeanPostProcessor Before处理
		Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);
		
		try {
			invokeInitMethods(beanName, wrappedBean, beanDefinition);
		} catch (Exception e) {
			throw new BeansException("Invocation of init method of bean[" + beanName + "] failed");
		}
		
		// 执行BeanPostProcessor After处理
		wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
		return wrappedBean;
	}
	
	protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
		Constructor constructorToUse = null;
		Class<?> beanClass = beanDefinition.getBeanClass();
		Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
		for (Constructor constructor : declaredConstructors) {
			if (null != args && constructor.getParameterTypes().length == args.length) {
				constructorToUse = constructor;
				break;
			}
		}
		return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);
	}

	/**
	 * Bean属性填充
	 * @param beanName
	 * @param bean
	 * @param beanDefinition
	 * @author chenyuxian
	 * @date 2021-07-01
	 */
	protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
		try {
			PropertyValues propertyValues = beanDefinition.getPropertyValues();
			for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
				String name = propertyValue.getName();
				Object value = propertyValue.getValue();
				if (value instanceof BeanReference) {
					BeanReference beanReference = (BeanReference) value;
					value = getBean(beanReference.getBeanName());
				}
				BeanUtil.setFieldValue(bean, name, value);
			}
		} catch (Exception e) {
			throw new BeansException("Error settting property values: " + beanName);
		}
	}

	public InstantiationStrategy getInstantiationStrategy() {
		return instantiationStrategy;
	}

	public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
		this.instantiationStrategy = instantiationStrategy;
	}
	
	private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
		// 实现接口 InitializingBean
		if(bean instanceof InitializingBean) {
			((InitializingBean) bean).afterPropertiesSet();
		}
		
		// 注解配置init-method(判断是为了避免二次执行销毁)
		String initMethodName = beanDefinition.getInitMethodName();
		if(StrUtil.isNotEmpty(initMethodName)) {
			Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
			if(null == initMethod) {
				throw new BeansException("Could not find an init method named" + initMethodName + "on bean with me" + beanName + "");
			}
			initMethod.invoke(bean);
		}
	}
	
	@Override
	public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
			throws BeansException {
		Object result = existingBean;
		for(BeanPostProcessor processor : getBeanPostProcessors()) {
			Object current = processor.postProcessBeforeInitialization(result, beanName);
			if(null == current) return result;
			result = current;
		}
		return result;
	}
	
	@Override
	public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
			throws BeansException {
		Object result = existingBean;
		for(BeanPostProcessor processor : getBeanPostProcessors()) {
			Object current = processor.postProcessAfterInitialization(result, beanName);
			if(null == current) return result;
			result = current;
		}
		return result;
	}
	
	protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
		// 非Singleton类型的Bean不执行销毁方法
		if(!beanDefinition.isSingleton()) return;
		
		if(bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
			registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
		}
	}

}
