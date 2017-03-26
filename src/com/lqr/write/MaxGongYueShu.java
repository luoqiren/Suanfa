package com.lqr.write;

public class MaxGongYueShu {

	public static void main(String[] args) {
		int s1 = 30, s2 = 40; // 给定两个整数
		System.out.println("最小值 == " + getMin(s1, s2));
		System.out.println("最大值 == " + getMax(s1, s2));
		System.out.println("递减法得最大公约数 == " + gcd1(s1, s2));
		System.out.println("累加器法得最大公约数 == " + gcd2(s1, s2));
		System.out.println("辗转相除法得最大公约数 == " + gcd3(s1, s2));
	}

	public static int getMax(int s1, int s2) // 返回两个整数中的最大值
	{
		return (s1 > s2) ? s1 : s2;
	}

	public static int getMin(int s1, int s2) // 返回两个整数中的最小值
	{
		return (s1 > s2) ? s2 : s1;
	}

	public static int gcd1(int s1, int s2) // 递减法求两个整数的最大公约数
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

	public static int gcd2(int s1, int s2) // 累加器法求最大公约数
	{
		int common = getMax(s1, s2);

		if (s1 == s2 || s1 == 0 || s2 == 0)
			return common;

		else
			for (int i = 1; i <= getMin(s1, s2); i++) // 累加
			{
				if (s1 % i == 0 && s2 % i == 0)
					common = i;
			}

		return common;
	}

	public static int gcd3(int s1, int s2) // 辗转相除法求最大公约数
	{
		int common = getMax(s1, s2);

		if (s1 == s2 || s1 == 0 || s2 == 0)

			return common;
		else {
			int max, min;
			max = getMax(s1, s2);
			min = getMin(s1, s2);
			int temp = max % min;
			System.out.println("辗转相除法得公约数：");
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
