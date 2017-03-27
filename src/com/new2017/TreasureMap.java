package com.new2017;

/**
 * 主题: 藏宝图
 * 
 * @author Qi 
 * 牛牛拿到了一个藏宝图，顺着藏宝图的指示，牛牛发现了一个藏宝盒，藏宝盒上有一个机关，机关每次会显示两个字符串 s 和
 *  t，根据古老的传说，牛牛需要每次都回答 t 是否是 s 的子序列。注意，子序列不要求在原字符串中是连续的，
 *  例如串 abc，它的子序列就有 {空串, a, b, c, ab, ac, bc, abc} 8 种。
 * 输入描述： 每个输入包含一个测试用例。每个测试用例包含两行长度不超过 10 的不包含空格的可见 ASCII 字符串。
 * 输出描述： 输出一行 “Yes” 或者 “No” 表示结果。
 * 
 * 输入例子： group.jobbole.com ooo
 * 输出例子: Yes
 */
public class TreasureMap {
	static String YES="YES";
	static String NO="NO";
	public static void main(String[] args) {
		String s = "group.jobbole.com";
		String t = "x";
		
		System.out.println(isTreasureMap(s, t));
	}
	/**
	* <p>Description: </p>
	* @author Qi
	* @date 20170327
	* @param s
	* @param t
	* @return
	 */
	private static String isTreasureMap(String s, String t) {
		if("".equals(t)){
			System.out.println("YES");
			return YES;
		}
		StringBuffer sbf = new StringBuffer(s);
		char tmp = ' ';
		int sIndex = 0;
		for (int i = 0; i < t.length(); i++) {
			tmp = t.charAt(i);
			sIndex = sbf.indexOf(tmp+"");
			if(sIndex<0){
				return NO;
			}
			sbf.replace(sIndex, sIndex+1, " ");
		}
		return  YES;
	}
}
