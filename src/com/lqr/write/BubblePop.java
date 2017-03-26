package com.lqr.write;

public class BubblePop {
	
	static int [] index = {6,1,5,4,8,7,10}; // => 1 4 5 6 7 8 10
	
	
	public static void main(String[] args) {
		/**
		 * 冒泡规则:
		 * 1.比较两个数字
		 * 2.如果左边数字大，则两个数字交换位置
		 * 3.向右移动一个位置，比较接下去的2个数字
		 * 
		 */
		
		int len = index.length;
		int tmp = 0;
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				
				if(index[i] < index[j]){
					tmp = index[i];
					index[i] = index[j];
					index[j] = tmp;
				}
				
			}
		}
		

		for (int i = 0; i < len; i++) {
			System.out.print(index[i]);
			System.out.print("  ");
		}
		
	}
}
