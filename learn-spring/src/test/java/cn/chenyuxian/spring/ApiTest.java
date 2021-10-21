package cn.chenyuxian.spring;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;

import cn.chenyuxian.spring.bean.IUserService;
import cn.chenyuxian.spring.bean.UserDao;
import cn.chenyuxian.spring.bean.UserService;
import cn.chenyuxian.spring.bean.UserServiceInterceptor;
import cn.chenyuxian.spring.common.MyBeanFactoryPostProcessor;
import cn.chenyuxian.spring.common.MyBeanPostProcessor;
import cn.chenyuxian.spring.event.CustomEvent;
import cn.hutool.core.io.IoUtil;
import learn.spring.aop.AdvisedSupport;
import learn.spring.aop.MethodMatcher;
import learn.spring.aop.TargetSource;
import learn.spring.aop.aspectj.AspectJExpressionPointcut;
import learn.spring.aop.framework.Cglib2AopProxy;
import learn.spring.aop.framework.JdkDynamicAopProxy;
import learn.spring.beans.PropertyValue;
import learn.spring.beans.PropertyValues;
import learn.spring.beans.factory.config.BeanDefinition;
import learn.spring.beans.factory.config.BeanReference;
import learn.spring.beans.factory.support.DefaultListableBeanFactory;
import learn.spring.beans.factory.xml.XmlBeanDefinitionReader;
import learn.spring.context.support.ClassPathXmlApplicationContext;
import learn.spring.core.io.DefaultResourceLoader;
import learn.spring.core.io.Resource;

public class ApiTest {

	private DefaultResourceLoader resourceLoader;
	
	@Before
	public void init() {
		resourceLoader = new DefaultResourceLoader();
	}
	
	@Test
	public void test_classpath() throws IOException {
		resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource("classpath:important.properties");
		InputStream in = resource.getInputStream();
		String content = IoUtil.readUtf8(in);
		System.out.println(content);
	}
	
	@Test
	public void test_file() throws IOException {
		Resource resource = resourceLoader.getResource("src/test/resources/important.properties");
		InputStream in = resource.getInputStream();
		String content = IoUtil.readUtf8(in);
		System.out.println(content);
	}
	
	@Test
	public void test_url() throws IOException {
		Resource resource = resourceLoader.getResource("https://github.com/YXCLING/learn-java/blob/master/src/test/resources/important.properties");
		InputStream in = resource.getInputStream();
		String content = IoUtil.readUtf8(in);
		System.out.println(content);
	}
	
	@Test
	public void test_BeanFactory() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));
		PropertyValues propertyValues = new PropertyValues();
		propertyValues.addPropertyValue(new PropertyValue("uid", "10001"));
		propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));
		BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
		beanFactory.registerBeanDefinition("userService", beanDefinition);
		UserService userService = (UserService) beanFactory.getBean("userService");
		userService.queryUserInfo();
	}
	
	@Test
	public void test_xml() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions("classpath:spring.xml");
		UserService userService = beanFactory.getBean("userService", UserService.class);
		String result = userService.queryUserInfo();
		System.out.println("测试结果:" + result);
	}
	
	@Test
	public void test_BeanFactoryPostProcessorAndBeanPostProcessor() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions("classpath:spring.xml");
		MyBeanFactoryPostProcessor beanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
		beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
		MyBeanPostProcessor beanPostProcessor = new MyBeanPostProcessor();
		beanFactory.addBeanPostProcessor(beanPostProcessor);
		UserService userService = beanFactory.getBean("userService", UserService.class);
		String result = userService.queryUserInfo();
		System.out.println("测试结果:" + result);
	}
	
	@Test
	public void test_xml2() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
		UserService userService = applicationContext.getBean("userService", UserService.class);
		String result = userService.queryUserInfo();
		System.out.println("测试结果:" + result);
	}
	
	@Test
	public void test_xml3() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
		applicationContext.registerShutdownHook();
		UserService userService = applicationContext.getBean("userService", UserService.class);
		String result = userService.queryUserInfo();
		System.out.println("测试结果:" + result);
	}
	
	@Test
	public void test_xml4() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
		applicationContext.registerShutdownHook();
		
		UserService userService = applicationContext.getBean("userService", UserService.class);
		String result = userService.queryUserInfo();
		System.out.println("测试结果:" + result);
		System.out.println("ApplicationContextAware:" + userService.getApplicationContext());
		System.out.println("BeanFactoryAware:" + userService.getBeanFactory());
	}
	
	@Test
	public void test_prototype() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
		applicationContext.registerShutdownHook();
		
		UserService userService = applicationContext.getBean("userService", UserService.class);
		UserService userService2 = applicationContext.getBean("userService", UserService.class);
		
		System.out.println(userService);
		System.out.println(userService2);
		
		System.out.println(userService + "十六进制哈希:" + Integer.toHexString(userService.hashCode()));
		System.out.println(ClassLayout.parseInstance(userService).toPrintable());
	}
	
	@Test
	public void test_event() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
		applicationContext.publishEvent(new CustomEvent(applicationContext, 12132312L, "成功了"));
		applicationContext.registerShutdownHook();
	}
	
	@Test
	public void test_proxy_method() {
		Object targetObj = new UserService();
		UserService proxy = (UserService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), targetObj.getClass().getInterfaces(), new InvocationHandler() {
			
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				return null;
			}
		});
	}
	
	@Test
	public void test_aop() throws NoSuchMethodException, SecurityException {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* cn.chenyuxian.spring.bean.UserService.*(..))");
		Class<UserService> clazz = UserService.class;
		Method method = clazz.getDeclaredMethod("queryUserInfo", null);
		System.out.println(pointcut.matcher(clazz));
		System.out.println(pointcut.matches(method, clazz));
	}
	
	@Test
	public void test_dynamic() {
		IUserService userService = new UserService();
		
		AdvisedSupport advisedSupport = new AdvisedSupport();
		advisedSupport.setTargetSource(new TargetSource(userService));
		advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
		advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution (* cn.chenyuxian.spring.bean.IUserService.*(..))"));
		
		IUserService proxy_jdk = (IUserService) new JdkDynamicAopProxy(advisedSupport).getProxy();
		System.out.println("测试结果:" + proxy_jdk.queryUserInfo());
		
		IUserService proxy_cglib = (IUserService) new Cglib2AopProxy(advisedSupport);
		System.out.println("测试结果:" + proxy_cglib.register("花花"));
	}
}
