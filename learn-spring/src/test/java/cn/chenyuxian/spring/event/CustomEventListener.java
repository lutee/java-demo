package cn.chenyuxian.spring.event;

import java.time.LocalDateTime;

import cn.chenyuxian.spring.context.ApplicationListener;

public class CustomEventListener implements ApplicationListener<CustomEvent>{

	@Override
	public void onApplicationEvent(CustomEvent event) {
		System.out.println("收到:" + event.getSource() + "消息:时间" + LocalDateTime.now());
		System.out.println("消息:" + event.getId() + "," + event.getMessage());
	}

	
}
