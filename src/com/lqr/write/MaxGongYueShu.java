package com.lqr.write;

public class MaxGongYueShu {

	public static void main(String[] args) {
		int s1 = 30, s2 = 40; // ������������
		System.out.println("��Сֵ == " + getMin(s1, s2));
		System.out.println("���ֵ == " + getMax(s1, s2));
		System.out.println("�ݼ��������Լ�� == " + gcd1(s1, s2));
		System.out.println("�ۼ����������Լ�� == " + gcd2(s1, s2));
		System.out.println("շת����������Լ�� == " + gcd3(s1, s2));
	}

	public static int getMax(int s1, int s2) // �������������е����ֵ
	{
		return (s1 > s2) ? s1 : s2;
	}

	public static int getMin(int s1, int s2) // �������������е���Сֵ
	{
		return (s1 > s2) ? s2 : s1;
	}

	public static int gcd1(int s1, int s2) // �ݼ������������������Լ��
	{
		int common = getMax(s1, s2);

		if (s1 == s2 || s1 == 0 || s2 == 0)

			return common;

		else
			while (s1 != s2) {
				common = getMax(s1, s2) - getMin(s1, s2);
				s1 = getMin(s1, s2);
				s2 = common;
			}

		return common;
	}

	public static int gcd2(int s1, int s2) // �ۼ����������Լ��
	{
		int common = getMax(s1, s2);

		if (s1 == s2 || s1 == 0 || s2 == 0)
			return common;

		else
			for (int i = 1; i <= getMin(s1, s2); i++) // �ۼ�
			{
				if (s1 % i == 0 && s2 % i == 0)
					common = i;
			}

		return common;
	}

	public static int gcd3(int s1, int s2) // շת����������Լ��
	{
		int common = getMax(s1, s2);

		if (s1 == s2 || s1 == 0 || s2 == 0)

			return common;
		else {
			int max, min;
			max = getMax(s1, s2);
			min = getMin(s1, s2);
			int temp = max % min;
			System.out.println("շת������ù�Լ����");
			System.out.println(temp);
			while (temp != 0) {
				max = min;
				min = temp;
				temp = max % min;
				System.out.println(temp);
			}

			return min;
		}
	}
}
