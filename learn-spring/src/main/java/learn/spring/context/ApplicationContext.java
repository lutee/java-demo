package learn.spring.context;

import learn.spring.beans.factory.HierarchicalBeanFactory;
import learn.spring.beans.factory.ListableBeanFactory;
import learn.spring.core.io.ResourceLoader;

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher{

}
