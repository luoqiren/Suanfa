package com.lqr.proxyTest;

//�ͻ��˵���
public class Client {
	public static void main(String[] args) {
		Subject sub = new ProxySubject();
		sub.request();
	}
}