package learn.spring.context.event;

import learn.spring.context.ApplicationContext;
import learn.spring.context.ApplicationEvent;

public class ApplicationContextEvent extends ApplicationEvent{

	public ApplicationContextEvent(Object source) {
		super(source);
	}

	public final ApplicationContext getApplicationContext() {
		return (ApplicationContext) getSource();
	}
	
}
