package com.demo.book;

public class BubblePop {
	
	static int [] index = {10,6,1,5,4,8,7,2}; // => 1 4 5 6 7 8 10
	
	static int len = index.length;
	public static void main(String[] args) {
		/**
		 * ð�ݹ���:
		 * 1.�Ƚ���������
		 * 2.���������ִ����������ֽ���λ��
		 * 3.�����ƶ�һ��λ�ã��ȽϽ���ȥ��2������
		 * 
		 */
		System.out.println("����ǰ:");
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
					
					System.out.println("��������: index["+inner+"]="+index[inner] +" | index["+(inner+1)+"]="+index[inner+1]);
					System.out.print("���ε�����:");
					display();
				}
			}
		}
		
		System.out.println("-------------------------------------------");
		System.out.println("�����");
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
