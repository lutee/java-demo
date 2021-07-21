package cn.chenyuxian.spring.aop;

public interface Pointcut {

	ClassFilter getClassFilter();
	
	MethodMatcher getMethodMatcher();
}
