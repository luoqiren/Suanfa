package com.test;

import java.math.BigInteger;
import java.util.Scanner;

public class PowerOfTwo {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入一个整字, 是否是2的乘方:");
		String arg = sc.nextLine();
		
		if(arg.length()>0){
			int intArg = Integer.valueOf(arg);
			
			if(isPowerOf2(intArg)){
				System.out.println(arg + " 是整数2的乘方!!");
			}else{
				System.out.println(arg + " 不是整数2的乘方...");
				
			}
			
			System.out.println(Integer.bitCount(intArg));
			
			BigInteger big = new BigInteger("100");big.gcd(new BigInteger("10"));
			
			System.out.println(2<<1);
		}
		sc.close();
	}

	private static boolean isPowerOf2(int intArg) {
		System.out.println(intArg & intArg);
		return (intArg & intArg - 1) == 0 ;
	}

}
