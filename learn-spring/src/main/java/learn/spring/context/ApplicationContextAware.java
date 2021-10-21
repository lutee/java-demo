package learn.spring.context;

import learn.spring.beans.BeansException;

public interface ApplicationContextAware {
	
	void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
