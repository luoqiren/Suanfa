package com.new2017;

import java.util.HashMap;

/**
 * @author Qi
 *名企笔试：去哪儿2015研发笔试题（首个重复字符）
 *对于一个字符串，请设计一个高效算法，找到第一次重复出现的字符。
 *给定一个字符串(不一定全为字母)A及它的长度n。请返回第一个重复出现的字符。
 *保证字符串中有重复字符，字符串的长度小于等于500。
 *
 *测试样例：
 * "qywyer23tdd"，11
 * 返回：y
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
	
	//无汉字情况下最强
	public static char findFirstRepeat2(String A, int n) {
        //ASSCI表共256个字符
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
