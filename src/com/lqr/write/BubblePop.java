package com.lqr.write;

public class BubblePop {
	
	static int [] index = {6,1,5,4,8,7,10}; // => 1 4 5 6 7 8 10
	
	
	public static void main(String[] args) {
		/**
		 * ð�ݹ���:
		 * 1.�Ƚ���������
		 * 2.���������ִ����������ֽ���λ��
		 * 3.�����ƶ�һ��λ�ã��ȽϽ���ȥ��2������
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
