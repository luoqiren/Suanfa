package com.lqr.staticProxiy;

import java.util.List;

//������:�����־���  
public class UserServProxyImpl implements IUserServ {
	// ����Ŀ�����(UserServImpl)
	// �������(UserServProxyImpl)
	// ����Ŀ�����
	private IUserServ iuserServ;// = new UserServImpl();

	public UserServProxyImpl(IUserServ iuserServ) {
		this.iuserServ = iuserServ;
	}

	public int deleteUserById(User user) {
		beforeLog();
		// ����Ŀ������﷽��
		iuserServ.deleteUserById(user);
		afterLog();
		return 0;
	}

	public List<User> findAllUser() {
		beforeLog();
		// ����Ŀ������﷽��
		iuserServ.findAllUser();
		afterLog();
		return null;
	}

	public int saveUser(User user) {
		beforeLog();
		// ����Ŀ������﷽��
		iuserServ.saveUser(user);
		afterLog();
		return 0;
	}

	private void beforeLog() {
		System.out.println("��ʼִ��");
	}

	private void afterLog() {
		System.out.println("ִ�����");
	}
}
