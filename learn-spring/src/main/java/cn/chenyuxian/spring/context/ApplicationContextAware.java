package cn.chenyuxian.spring.context;

import cn.chenyuxian.spring.beans.BeansException;

public interface ApplicationContextAware {
	
	void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
