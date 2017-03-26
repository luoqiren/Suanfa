package com.lqr.write;

public class Digui {

	public void testDigui(int min){
		System.out.println("min:"+min);
		min -- ;
		if(min == 0){
			return ;
		}
		testDigui(min);
		System.out.println("min:"+min);
	}
	
	public static void main(String[] args) {
		Digui digui = new Digui();
		digui.testDigui(3);
	}

}
