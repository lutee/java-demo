package cn.chenyuxian.spring.event;

import learn.spring.context.ApplicationListener;
import learn.spring.context.event.ContextCloseEvent;

public class ContextClosedEventListener implements ApplicationListener<ContextCloseEvent> {

	@Override
	public void onApplicationEvent(ContextCloseEvent event) {
		// TODO Auto-generated method stub
		System.out.println("关闭事件:" + this.getClass().getName());
	}

}
