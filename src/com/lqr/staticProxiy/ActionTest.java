package com.lqr.staticProxiy;

public class ActionTest {
	public static void main(String[] args) {
		// �û����ʴ������---��Ϣ->Ŀ�����
		IUserServ iuserServ = new UserServProxyImpl(new UserServImpl());
		iuserServ.findAllUser();
	}
}
