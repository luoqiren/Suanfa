package com.lqr.proxyTest;

//代理角色
public class ProxySubject extends Subject {
	private RealSubject realSubject; // 以真实角色作为代理角色的属性

	public ProxySubject() {
	}

	// 该方法封装了真实对象的request方法
	@Override
	public void request() {
		preRequest();
		if (realSubject == null) {
			realSubject = new RealSubject();
		}
		realSubject.request(); // 此处执行真实对象的request方法
		postRequest();
	}

	private void preRequest() {
		System.out.println("秘书去找局长");
	}

	private void postRequest() {
		System.out.println("秘书回来了");
	}
}