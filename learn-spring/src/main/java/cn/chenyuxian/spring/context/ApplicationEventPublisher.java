package cn.chenyuxian.spring.context;

public interface ApplicationEventPublisher {
	
	void publishEvent(ApplicationEvent event);
}
