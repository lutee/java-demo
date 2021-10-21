package cn.chenyuxian.spring.common;

import cn.chenyuxian.spring.bean.UserService;
import learn.spring.beans.BeansException;
import learn.spring.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor{

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if("userService".equals(beanName)) {
			UserService userService = (UserService) bean;
			userService.setLocation("改为:北京");
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
