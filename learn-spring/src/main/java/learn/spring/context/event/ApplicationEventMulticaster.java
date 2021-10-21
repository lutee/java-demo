package learn.spring.context.event;

import learn.spring.context.ApplicationEvent;
import learn.spring.context.ApplicationListener;

public interface ApplicationEventMulticaster {
	
	void addApplicationListener(ApplicationListener<?> listener);
	
	void removeApplicationListener(ApplicationListener<?> listener);
	
	void multicastEvent(ApplicationEvent event);
}
