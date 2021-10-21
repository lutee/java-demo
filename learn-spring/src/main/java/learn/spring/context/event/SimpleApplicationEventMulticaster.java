package learn.spring.context.event;

import learn.spring.beans.factory.BeanFactory;
import learn.spring.context.ApplicationEvent;
import learn.spring.context.ApplicationListener;

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
