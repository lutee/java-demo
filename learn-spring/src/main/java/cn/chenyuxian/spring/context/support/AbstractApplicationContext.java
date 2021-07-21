package cn.chenyuxian.spring.context.support;

import java.util.Collection;
import java.util.Map;

import cn.chenyuxian.spring.beans.BeansException;
import cn.chenyuxian.spring.beans.factory.ConfigurableListableBeanFactory;
import cn.chenyuxian.spring.beans.factory.config.BeanFactoryPostProcessor;
import cn.chenyuxian.spring.beans.factory.config.BeanPostProcessor;
import cn.chenyuxian.spring.context.ApplicationEvent;
import cn.chenyuxian.spring.context.ApplicationListener;
import cn.chenyuxian.spring.context.ConfigurableApplicationContext;
import cn.chenyuxian.spring.context.event.ApplicationEventMulticaster;
import cn.chenyuxian.spring.context.event.ContextCloseEvent;
import cn.chenyuxian.spring.context.event.ContextRefreshedEvent;
import cn.chenyuxian.spring.context.event.SimpleApplicationEventMulticaster;
import cn.chenyuxian.spring.core.io.DefaultResourceLoader;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext{
	
	public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";
	
	private ApplicationEventMulticaster applicationEventMulticaster;
	
	@Override
	public void refresh() throws BeansException {
		
		// 创建BeanFactory，并加载BeanDefinition
		refreshBeanFactory();
		
		// 获取BeanFactory
		ConfigurableListableBeanFactory beanFactory = getBeanFactory();
		
		// 添加ApplicationContextAwareProcessor，让继承自ApplicationContextAware的Bean对象都能感知所属的ApplicationContext
		beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
		
		// 在Bean实例化之前，执行BeanFactoryPostProcessor
		invokeBeanFactoryPostProcessors(beanFactory);
		
		// BeanPostProcessor 需要提前于其他Bean对象实例化之前执行注册操作
		registerBeanPostProcessors(beanFactory);
		
		// 初始化事件发布者
		initApplicationEventMulticaster();
		
		// 注册事件监听器
		registerListeners();
		
		// 提前实例化单例Bean对象
		beanFactory.preInstantiateSingletons();
		
		// 发布容器刷新完成事件
		finishRefresh();
	}
	
	protected abstract void refreshBeanFactory() throws BeansException;
	
	protected abstract ConfigurableListableBeanFactory getBeanFactory();
	
	private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
		Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
		for(BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
			beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
		}
	}
	
	private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
		Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
		for(BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
			beanFactory.addBeanPostProcessor(beanPostProcessor);
		}
	}
	
	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
		return getBeanFactory().getBeansOfType(type);
	}
	
	@Override
	public String[] getBeanDefinitionNames() {
		return getBeanFactory().getBeanDefinitionNames();
	}

	@Override
	public Object getBean(String name) throws BeansException {
		return getBeanFactory().getBean(name);
	}
	
	@Override
	public Object getBean(String name, Object... args) throws BeansException {
		return getBeanFactory().getBean(name, args);
	}
	
	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return getBeanFactory().getBean(name, requiredType);
	}
	
	@Override
	public void registerShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(this::close));
	}
	
	@Override
	public void close() {
		// 发布容器关闭事件
		publishEvent(new ContextCloseEvent(this));
		
		// 执行销毁单例bean的销毁方法
		getBeanFactory().destroySingletons();
	}
	
	private void initApplicationEventMulticaster() {
		ConfigurableListableBeanFactory beanFactory = getBeanFactory();
		applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
		beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
	}
	
	private void registerListeners() {
		Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
		for (ApplicationListener listener : applicationListeners) {
			applicationEventMulticaster.addApplicationListener(listener);
		}
	}
	
	private void finishRefresh() {
		publishEvent(new ContextRefreshedEvent(this));
	}
	
	@Override
	public void publishEvent(ApplicationEvent event) {
		applicationEventMulticaster.multicastEvent(event);
	}
	
}
