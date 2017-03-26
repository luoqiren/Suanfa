package com.demo.book;

public class BubblePop {
	
	static int [] index = {10,6,1,5,4,8,7,2}; // => 1 4 5 6 7 8 10
	
	static int len = index.length;
	public static void main(String[] args) {
		/**
		 * 冒泡规则:
		 * 1.比较两个数字
		 * 2.如果左边数字大，则两个数字交换位置
		 * 3.向右移动一个位置，比较接下去的2个数字
		 * 
		 */
		System.out.println("排序前:");
		display();
		int tmp = 0;
		
		int outer, inner;
		System.out.println("-------------------------------------------");
		for(outer=len-1; outer>1; outer--){
			for (inner = 0; inner < outer; inner++) {
				if(index[inner] > index[inner+1]){
					tmp = index[inner];
					index[inner] = index[inner+1];
					index[inner+1] = tmp;
					
					System.out.println("调整的是: index["+inner+"]="+index[inner] +" | index["+(inner+1)+"]="+index[inner+1]);
					System.out.print("本次调整后:");
					display();
				}
			}
		}
		
		System.out.println("-------------------------------------------");
		System.out.println("排序后");
		display();
		System.out.println();
	}
	
	public static void display(){
		for (int i = 0; i < len; i++) {
			System.out.print(index[i]);
			System.out.print("  ");
		}
		System.out.println();
	}
}
