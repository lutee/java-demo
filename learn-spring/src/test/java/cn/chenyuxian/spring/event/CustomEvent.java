package cn.chenyuxian.spring.event;

import cn.chenyuxian.spring.context.event.ApplicationContextEvent;
import lombok.Data;

@Data
public class CustomEvent extends ApplicationContextEvent {

	public CustomEvent(Object source, Long id, String message) {
		super(source);
		this.id = id;
		this.message = message;
	}

	private Long id;
	private String message;

}
