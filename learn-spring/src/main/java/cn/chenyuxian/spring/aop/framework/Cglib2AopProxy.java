package cn.chenyuxian.spring.aop.framework;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import cn.chenyuxian.spring.aop.AdvisedSupport;

public class Cglib2AopProxy implements AopProxy{

	private final AdvisedSupport advised;
	
	public Cglib2AopProxy(AdvisedSupport advised) {
		super();
		this.advised = advised;
	}

	@Override
	public Object getProxy() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(advised.getTargetSource().getTarget().getClass());
		enhancer.setInterfaces(advised.getTargetSource().getTargetClass());
		enhancer.setCallback(new DynamicAdvisedInterceptor(advised));
		return enhancer.create();
	}
	
	private static class DynamicAdvisedInterceptor implements MethodInterceptor{

		private final AdvisedSupport advised;
		
		
		public DynamicAdvisedInterceptor(AdvisedSupport advised) {
			super();
			this.advised = advised;
		}


		@Override
		public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3) throws Throwable {
			CglibMethodInvocation methodInvocation = new CglibMethodInvocation(advised.getTargetSource().getTarget(), arg1, arg2, arg3);
			if(advised.getMethodMatcher().matches(arg1, advised.getTargetSource().getTarget().getClass())) {
				return advised.getMethodInterceptor().invoke(methodInvocation);
			}
			return methodInvocation.proceed();
		}

		
	}
	
	private static class CglibMethodInvocation extends ReflectiveMethodInvocation {
		
		private final MethodProxy methodProxy;
		
		public CglibMethodInvocation(Object target, Method method, Object[] arguments, MethodProxy methodProxy) {
			super(target, method, arguments);
			this.methodProxy = methodProxy;
		}

		
	}

}
