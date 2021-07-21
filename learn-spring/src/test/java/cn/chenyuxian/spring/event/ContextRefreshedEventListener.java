package cn.chenyuxian.spring.event;

import cn.chenyuxian.spring.context.ApplicationListener;
import cn.chenyuxian.spring.context.event.ContextRefreshedEvent;

public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		System.out.println("刷新事件:" + this.getClass().getName());
	}

	
}
