package com.lqr.staticProxiy;

import java.util.List;

//代理类:完成日志输出  
public class UserServProxyImpl implements IUserServ {
	// 访问目标对象(UserServImpl)
	// 代理对象(UserServProxyImpl)
	// 创建目标对象
	private IUserServ iuserServ;// = new UserServImpl();

	public UserServProxyImpl(IUserServ iuserServ) {
		this.iuserServ = iuserServ;
	}

	public int deleteUserById(User user) {
		beforeLog();
		// 调用目标对象里方法
		iuserServ.deleteUserById(user);
		afterLog();
		return 0;
	}

	public List<User> findAllUser() {
		beforeLog();
		// 调用目标对象里方法
		iuserServ.findAllUser();
		afterLog();
		return null;
	}

	public int saveUser(User user) {
		beforeLog();
		// 调用目标对象里方法
		iuserServ.saveUser(user);
		afterLog();
		return 0;
	}

	private void beforeLog() {
		System.out.println("开始执行");
	}

	private void afterLog() {
		System.out.println("执行完毕");
	}
}
