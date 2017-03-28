package com.new2017;

/**
 * 
 * @author lqr 
 * 名企笔试：2017网易游戏笔试（赛马）
 * 在一条无限长的跑道上，有N匹马在不同的位置上出发开始赛马。当开始赛马比赛后，所有的马开始以自己的速度一直匀速前进
 * 。每匹马的速度都不一样，且全部是同样的均匀随机分布。在比赛中当某匹马追上了前面的某匹马时，被追上的马就出局。
 * 请问按以上的规则比赛无限长的时间后，赛道上剩余的马匹数量的数学期望是多少
 * 
 * 
 * 输入描述:
 * 
 * 每个测试输入包含1个测试用例
 * 
 * 输入只有一行，一个正整数N
 * 
 * 1 <= N <= 1000
 * 输出描述:
 * 
 * 输出一个浮点数，精确到小数点后四位数字，表示剩余马匹数量的数学期望
 * 输入例子:
 * 1
 * 2
 * 输出例子:
 * 1.0000
 * 1.5000
 *
 */
public class HorseRacing {
	//思路：
/*	马的速度不同，则一定能由大到小排列。假设是a1＞a2＞……＞an 那么a1在任何位置都可以存活 a2必须在a1后面才可以存活，因为路是无限长，
	所以概率是1/2 a3同理需要在a1和a2后面才能活，概率就是1/3 以此类推，期望是: 1+1/2+1/3+…..+1/n*/
	public static void main(String[] args) {
		System.out.println(calcMean(2));
	}
	
	public static float calcMean(int input){
		float resultMean = 0f;
		for (int i = 1; i <= input; i++) {
			System.out.println(i);
			resultMean += 1.0/i;
		}
		
		
		return resultMean;
	}
	
}
