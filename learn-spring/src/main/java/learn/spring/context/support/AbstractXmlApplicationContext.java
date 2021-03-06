package learn.spring.context.support;

import learn.spring.beans.factory.support.DefaultListableBeanFactory;
import learn.spring.beans.factory.xml.XmlBeanDefinitionReader;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{
	
	@Override
	protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
		String[] configLocations = getConfigLocations();
		if(null != configLocations) {
			beanDefinitionReader.loadBeanDefinitions(configLocations);
		}
	}
	
	protected abstract String[] getConfigLocations();
}
