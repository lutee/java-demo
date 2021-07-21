package cn.chenyuxian.spring.context.event;

import cn.chenyuxian.spring.context.ApplicationEvent;
import cn.chenyuxian.spring.context.ApplicationListener;

public interface ApplicationEventMulticaster {
	
	void addApplicationListener(ApplicationListener<?> listener);
	
	void removeApplicationListener(ApplicationListener<?> listener);
	
	void multicastEvent(ApplicationEvent event);
}
