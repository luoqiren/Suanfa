package com.lqr.proxyTest;

//��ʵ��ɫ��ʵ����Subject��request()����
public class RealSubject extends Subject {
	public RealSubject() {
	}

	public void request() {
		System.out.println("�ֳ������ˣ�");
	}
}