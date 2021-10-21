package learn.spring.context.event;

public class ContextCloseEvent extends ApplicationContextEvent{

	public ContextCloseEvent(Object source) {
		super(source);
	}
	
	
}
