package com.demo.book;

public class SelectPop {

	static int [] index = {10,6,1,5,4,8,7,2}; // => 1 4 5 6 7 8 10
	
	static int len = index.length;
	
	public static void main(String[] args) {
		System.out.println("ÅÅĞòÇ°:");
		display();
		System.out.println("-------------------------------------------");
		int tmp = 0;
		int outer, inner, min;
		
		for (outer = 0; outer < len-1; outer++) {
			System.out.println("--------outer:"+outer);
			min = outer;
			for (inner = outer+1; inner < len; inner++) {
				System.out.println("#### inner:"+inner);
				if(index[inner]<index[min]){
					min = inner;
					System.out.println("Change min index:"+min);
				}
				
				display();
			}
//			tmp = index[outer];
//			index[outer] = index[min];
//			index[min] = tmp;
			
			index[outer] = index[outer] + index[min];
			index[min] = index[outer] - index[min];
			index[outer] = index[outer] - index[min];
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
