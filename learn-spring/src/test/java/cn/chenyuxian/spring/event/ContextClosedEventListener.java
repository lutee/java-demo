package cn.chenyuxian.spring.event;

import cn.chenyuxian.spring.context.ApplicationListener;
import cn.chenyuxian.spring.context.event.ContextCloseEvent;

public class ContextClosedEventListener implements ApplicationListener<ContextCloseEvent> {

	@Override
	public void onApplicationEvent(ContextCloseEvent event) {
		// TODO Auto-generated method stub
		System.out.println("关闭事件:" + this.getClass().getName());
	}

}
