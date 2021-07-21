package cn.chenyuxian.spring.beans.factory;

public interface DisposableBean {
	
	void destroy() throws Exception;
}
