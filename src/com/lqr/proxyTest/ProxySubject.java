package com.lqr.proxyTest;

//�����ɫ
public class ProxySubject extends Subject {
	private RealSubject realSubject; // ����ʵ��ɫ��Ϊ�����ɫ������

	public ProxySubject() {
	}

	// �÷�����װ����ʵ�����request����
	@Override
	public void request() {
		preRequest();
		if (realSubject == null) {
			realSubject = new RealSubject();
		}
		realSubject.request(); // �˴�ִ����ʵ�����request����
		postRequest();
	}

	private void preRequest() {
		System.out.println("����ȥ�Ҿֳ�");
	}

	private void postRequest() {
		System.out.println("���������");
	}
}