package cn.chenyuxian.spring.bean;

import java.util.HashMap;
import java.util.Map;

public class UserDao {
	
	private static Map<String, String> hashMap = new HashMap<>();
	
	public String queryUserName(String uid) {
		return hashMap.get(uid);
	}
	
	public void initDataMethod() {
		System.out.println("执行: init-method");
		hashMap.put("10001", "chenyuxian");
		hashMap.put("10002", "就被谁");
		hashMap.put("10003", "阿毛");
	}
	
	public void destroyDataMethod() {
		System.out.println("执行: destroy-method");
		hashMap.clear();
	}
}
