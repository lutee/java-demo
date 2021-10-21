package learn.spring.aop;

public class TargetSource {

	private final Object target;

	public TargetSource(Object target) {
		super();
		this.target = target;
	}
	
	public Class<?>[] getTargetClass(){
		return this.target.getClass().getInterfaces();
	}
	
	public Object getTarget() {
		return this.target;
	}
}
