package cn.chenyuxian.spring.context.event;

import cn.chenyuxian.spring.beans.factory.BeanFactory;
import cn.chenyuxian.spring.context.ApplicationEvent;
import cn.chenyuxian.spring.context.ApplicationListener;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{
	
	public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
		setBeanFactory(beanFactory);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void multicastEvent(final ApplicationEvent event) {
		for (final ApplicationListener listener : getApplicationListeners(event)) {
			listener.onApplicationEvent(event);
		}
	}


}
