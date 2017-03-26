package com.demo.book;

public class InsertSort {
	static int [] index = {10,6,1,5,4,8,7,2}; // => 1 4 5 6 7 8 10
	
	static int len = index.length;
	
	public static void main(String[] args) {
		System.out.println("ÅÅĞòÇ°:");
		display();
		System.out.println("-------------------------------------------");
		int tmp = 0;
		int outer, inner;

		for (outer = 1; outer < len; outer++) {
			tmp = index[outer];
			inner = outer;
			while (inner>0 && index[inner-1]>tmp) {
				System.out.println("tmp:"+tmp+ " || outer:"+outer +"  || inner:"+inner);
				index[inner] = index[inner-1];
				--inner;
				display();
			}
			
			index[inner] = tmp;
			display();
		}
		
		System.out.println("-------------------------------------------");
		System.out.println("ÅÅĞòºó");
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
