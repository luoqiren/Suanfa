package com.lqr.write;

public class GongYueShu {

	public static void main(String[] args) {
		int s1 = 30, s2 = 40; // 给定两个整数
		int sqrtS1 = (int) Math.sqrt(s1);
		int sqrtS2 = (int) Math.sqrt(s2);
		
		System.out.println(sqrtS1 + "--" + sqrtS2);
	
		for (int i = 1; i <=s1; i++) {
			if(s1%i==0 && s2%i==0){
				System.out.println(i);
			}
		}
		
		
	}

	
	
}
