package cn.chenyuxian.spring.context;

import cn.chenyuxian.spring.beans.factory.HierarchicalBeanFactory;
import cn.chenyuxian.spring.beans.factory.ListableBeanFactory;
import cn.chenyuxian.spring.core.io.ResourceLoader;

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher{

}
