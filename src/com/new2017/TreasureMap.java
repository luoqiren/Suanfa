package com.new2017;

/**
 * ����: �ر�ͼ
 * 
 * @author Qi 
 * ţţ�õ���һ���ر�ͼ��˳�Ųر�ͼ��ָʾ��ţţ������һ���ر��У��ر�������һ�����أ�����ÿ�λ���ʾ�����ַ��� s ��
 *  t�����ݹ��ϵĴ�˵��ţţ��Ҫÿ�ζ��ش� t �Ƿ��� s �������С�ע�⣬�����в�Ҫ����ԭ�ַ������������ģ�
 *  ���紮 abc�����������о��� {�մ�, a, b, c, ab, ac, bc, abc} 8 �֡�
 * ���������� ÿ���������һ������������ÿ�����������������г��Ȳ����� 10 �Ĳ������ո�Ŀɼ� ASCII �ַ�����
 * ��������� ���һ�� ��Yes�� ���� ��No�� ��ʾ�����
 * 
 * �������ӣ� group.jobbole.com ooo
 * �������: Yes
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
