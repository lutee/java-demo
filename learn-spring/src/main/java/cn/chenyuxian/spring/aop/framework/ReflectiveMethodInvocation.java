package cn.chenyuxian.spring.aop.framework;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

public class ReflectiveMethodInvocation implements MethodInvocation{

	protected final Object target;
	
	protected final Method method;
	
	protected final Object[] arguments;
	
	public ReflectiveMethodInvocation(Object target, Method method, Object[] arguments) {
		super();
		this.target = target;
		this.method = method;
		this.arguments = arguments;
	}

	@Override
	public Object[] getArguments() {
		// TODO Auto-generated method stub
		return arguments;
	}

	@Override
	public Object proceed() throws Throwable {
		// TODO Auto-generated method stub
		return method.invoke(target, arguments);
	}

	@Override
	public Object getThis() {
		// TODO Auto-generated method stub
		return target;
	}

	@Override
	public AccessibleObject getStaticPart() {
		// TODO Auto-generated method stub
		return method;
	}

	@Override
	public Method getMethod() {
		return method;
	}

	
}
