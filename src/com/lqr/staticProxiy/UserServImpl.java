package com.lqr.staticProxiy;

import java.util.List;

public class UserServImpl implements IUserServ {  
	   public int deleteUserById(User user) {  
	       System.out.println("******ִ��ɾ������******");  
	       return 0;  
	   }  
	   public List<User> findAllUser() {  
	       System.out.println("*******ִ�в�ѯ����*******");  
	       return null;  
	   }  
	    public int saveUser(User user) {  
	        System.out.println("*******ִ����ӷ���********");  
	        return 0;  
	    }  
	}  
