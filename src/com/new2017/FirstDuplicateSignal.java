package com.new2017;

import java.util.HashMap;

/**
 * @author Qi
 *������ԣ�ȥ�Ķ�2015�з������⣨�׸��ظ��ַ���
 *����һ���ַ����������һ����Ч�㷨���ҵ���һ���ظ����ֵ��ַ���
 *����һ���ַ���(��һ��ȫΪ��ĸ)A�����ĳ���n���뷵�ص�һ���ظ����ֵ��ַ���
 *��֤�ַ��������ظ��ַ����ַ����ĳ���С�ڵ���500��
 *
 *����������
 * "qywyer23tdd"��11
 * ���أ�y
 */
public class FirstDuplicateSignal {

	public static void main(String[] args) {
		System.out.println(findFirstRepeat("q2y2wyer23tdd",11));
		System.out.println(findFirstRepeat2("0q2y2wyer23tdd",11));
		
/*		boolean[] assci = new boolean[2];
		assci[0]=true;
		assci[1]=true;
		assci['p'] = false;
		System.out.println(assci[0] + "  " +assci[1]);*/
	}     
	
	public static char findFirstRepeat(String str, int n) {  
        HashMap<Character,Integer> map = new  HashMap<Character,Integer> ();  
        char c = 0 ;  
        for(int i=0;i<n;i++){  
            c = str.charAt(i);  
            if(map.containsKey(c)){  
                return c;  
            }  
            map.put(c, 1);  
        }  
        return c;  
    }
	
	//�޺����������ǿ
	public static char findFirstRepeat2(String A, int n) {
        //ASSCI��256���ַ�
        boolean[] assci = new boolean[256];
        char ch = ' ';
        for (int i = 0; i < n; i++) {
            ch = A.charAt(i);
            if (!assci[ch]) {
                assci[ch] = true;
            }else {
                break;
            }
        }
        return ch;
    }

}
