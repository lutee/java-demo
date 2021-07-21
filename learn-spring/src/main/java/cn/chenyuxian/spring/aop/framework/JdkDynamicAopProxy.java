package cn.chenyuxian.spring.aop.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.aopalliance.intercept.MethodInterceptor;

import cn.chenyuxian.spring.aop.AdvisedSupport;

public class JdkDynamicAopProxy implements AopProxy, InvocationHandler{

	private final AdvisedSupport advised;
	
	public JdkDynamicAopProxy(AdvisedSupport advised) {
		super();
		this.advised = advised;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if(advised.getMethodMatcher().matches(method, advised.getTargetSource().getTarget().getClass())) {
			MethodInterceptor methodInterceptor = advised.getMethodInterceptor();
			return methodInterceptor.invoke(new ReflectiveMethodInvocation(advised.getTargetSource(), method, args));
		}
		return method.invoke(advised.getTargetSource(), args);
	}

	@Override
	public Object getProxy() {
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), advised.getTargetSource().getTargetClass(), this);
	}

}
