package cn.chenyuxian.spring.bean;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class UserServiceInterceptor implements MethodInterceptor{

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		long start = System.currentTimeMillis();
		try {
			return invocation.proceed();			
		} finally {
			System.out.println("监控-begin by aop");
			System.out.println("方法名称:" + invocation.getMethod());
			System.out.println("方法耗时:" + (System.currentTimeMillis() - start));
			System.out.println("监控结束");
		}
	}

}
