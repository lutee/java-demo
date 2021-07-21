package cn.chenyuxian.spring.context.event;

import cn.chenyuxian.spring.context.ApplicationContext;
import cn.chenyuxian.spring.context.ApplicationEvent;

public class ApplicationContextEvent extends ApplicationEvent{

	public ApplicationContextEvent(Object source) {
		super(source);
	}

	public final ApplicationContext getApplicationContext() {
		return (ApplicationContext) getSource();
	}
	
}
