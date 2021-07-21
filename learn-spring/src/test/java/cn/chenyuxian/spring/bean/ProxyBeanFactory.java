package cn.chenyuxian.spring.bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import cn.chenyuxian.spring.beans.factory.FactoryBean;

public class ProxyBeanFactory implements FactoryBean<IUserDao>{

	@Override
	public IUserDao getObject() throws Exception {
		InvocationHandler handler = (proxy, method, args) -> {
			
			Map<String, String> hashMap = new HashMap<>();
			hashMap.put("10001", "chenyuxian");
			hashMap.put("10002", "就被谁");
			hashMap.put("10003", "阿毛");
			return "你被代理了" + method.getName() + ":" + hashMap.get(args[0].toString());
		};
		return (IUserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] {IUserDao.class}, handler);
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return IUserDao.class;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return true;
	}

}
