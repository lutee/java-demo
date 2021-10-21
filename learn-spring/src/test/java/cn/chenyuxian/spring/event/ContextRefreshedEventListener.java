package cn.chenyuxian.spring.event;

import learn.spring.context.ApplicationListener;
import learn.spring.context.event.ContextRefreshedEvent;

public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		System.out.println("刷新事件:" + this.getClass().getName());
	}

	
}
