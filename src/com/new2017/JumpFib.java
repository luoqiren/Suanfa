package com.new2017;

/**
 * ��̨������ + ��̬��̨������ �ⷨ����̬�滮�ݹ� ��
 * ��չ�Ķ���������дӵ�3�ʼ��ÿһ�����ǰ����֮��
 * http://baike.baidu.com/link?url=WSUji5LeLlbp7Uj8wtJoDZcW660LVFI84t28l4S4q_eUCLhKdj2uxMTekGEDCLMPwsJUpoB2gfBt7OEyWfG8UZdHmnW7zJnxDwqIMIFuv7Erpw54BAfmWr93r5fI-r_d6nPEBWAc53r_LY-cglhC-_
 * @author lqr
 * @date 2017-07-03
 */
public class JumpFib {

	public static void main(String[] args) {
		long startTimeOne = System.currentTimeMillis();
		System.out.println(solutionOne(3));
		long endTimeOne = System.currentTimeMillis();
		System.out.println("solutionOne cost seconds:" + (endTimeOne - startTimeOne) / 1000);
//
//		long startTimeTwo = System.currentTimeMillis();
//		System.out.println(solutionTwo(40));
//		long endTimeTwo = System.currentTimeMillis();
//		System.out.println("solutionTwo cost seconds:" + (endTimeTwo - startTimeTwo) / 1000);

		long terribleJumpStartTime = System.currentTimeMillis();
		System.out.println(terribleJump(3));
		long terribleJumpEndTime  = System.currentTimeMillis();
		System.out.println("solutionTwo cost seconds:" + (terribleJumpEndTime - terribleJumpStartTime) / 1000);
	}

	/***
	 * ��Ŀ������ һ��̨���ܹ���n�������һ�ο�����1����Ҳ������2�������ܹ��ж������������������㷨��ʱ�临�Ӷȡ�
	 * ͨ����Ŀ�����������Ժ������ؿ����������һ��Fibonacci���С�
	 *         | 1 n=1
	 *  F(n) = | 2 n=2
	 *         | F(n-1) +F(n-2)
	 */
	// �ݹ�ʵ�� 1
	public static long solutionOne(int stageNum) {
		// ����ݹ����
		// ������ͼ����������ķѵ�ʱ���������ģ��ָ������ġ����Լ�����㻺������ߵݹ��ٶȡ�
		if (stageNum <= 0) {
			return 0;
		} else if (1 == stageNum) {
			return 1;
		} else if (2 == stageNum) {
			return 2;
		}
		return solutionOne(stageNum - 1) + solutionOne(stageNum - 2);
	}

	// �ݹ�ʵ�� 2
	public static long solutionTwo(int stageNum) {
		// �Զ����µĶ�̬�滮
		long[] counter = new long[101];
		if (0 != counter[stageNum]) {
			return counter[stageNum];
		}
		// ����ݹ����
		if (stageNum <= 0) {
			return 0;
		} else if (1 == stageNum) {
			return counter[1] = 1;
		} else if (2 == stageNum) {
			return counter[2] = 2;
		}

		counter[stageNum] = solutionTwo(stageNum - 1) + solutionTwo(stageNum - 2);
		return counter[stageNum];
	}

	/***
	 * ������̬��̨������ ��Ŀ������ һ��̨���ܹ���n�������һ�ο�����1����Ҳ������2������Ҳ������n�������ܹ��ж������������������㷨��ʱ�临�Ӷ�.
	 * 
	 * ��Ŀ������ һ��̨���ܹ���n�������һ�ο�����1����Ҳ������2������Ҳ������n�������ܹ��ж������������������㷨��ʱ�临�Ӷȡ�
	 * 
	 * ��������Fib(n)��ʾ����n��̨�׵���������������ն��壬Fib(0)�϶���ҪΪ0������û�����塣���������趨Fib(0) = 1��n =
	 * 0�����������ͨ������ķ����ͻ�֪���� ǿ����Fib(0) = 1���кô���ps: Fib(0)���ڼ�����Ӱ�����ǽ��⣬���ǻ�Ӱ����������ķ�����⡣ 
	 * ��n = 1 ʱ�� ֻ��һ����������1������Fib(1) = 1; ��n =
	 * 2 ʱ�� ���������ķ�ʽ��һ�����Ͷ�������Fib(2) = 2; ������Ϊֹ������ͨ��̨����һ���ġ� ��n = 3
	 * ʱ�����������ķ�ʽ����һ������һ�׺󣬶�ӦFib(3-1)�������� ��һ���������׺󣬶�ӦFib(3-2)��������
	 * ��һ���������׺�ֻ����һ��������Fib(3) = Fib(2) + Fib(1)+ 1 = Fib(2) + Fib(1) + Fib(0) =
	 * 4; ��n =
	 * 4ʱ�������ַ�ʽ����һ������һ�ף���ӦFib(4-1)����������һ���������ף���ӦFib(4-2)����������һ���������ף���ӦFib(4-3)
	 * ����������һ�������Ľף�ֻ����һ�������� ���ԣ�Fib(4) = Fib(4-1) + Fib(4-2) + Fib(4-3) + 1 =
	 * Fib(4-1) + Fib(4-2) + Fib(4-3) + Fib(4-4) �������� 
	 * -----------������˼·------start----------------
	 * ��n = n ʱ������n�����ķ�ʽ����һ������һ�׺󣬺��滹��Fib(n-1)��������
	 * ��һ���������׺󣬺��滹��Fib(n-2)������.......................... ��һ������n�׺󣬺��滹��Fib(n-n)��������
	 * -----------������˼·-------end-----------------
	 * Fib(n) = Fib(n-1)+Fib(n-2)+Fib(n-3)+..........+Fib(n-n) =
	 * Fib(0)+Fib(1)+Fib(2)+.......+Fib(n-1)�� ͨ���������������Ǿ͵õ���ͨ�ʽ�� 
	 * Fib(n) = Fib(0)+Fib(1)+Fib(2)+.......+ Fib(n-2) + Fib(n-1) ��ˣ���
	 * Fib(n-1)=Fib(0)+Fib(1)+Fib(2)+.......+Fib(n-2) ��ʽ����ã�Fib(n)-Fib(n-1) =
	 * Fib(n-1) =====�� Fib(n) = 2*Fib(n-1) n >= 3 �����������Ҫ�ĵ��ƹ�ʽ��Fib(n) = 2*Fib(n-1) n >= 3
	 */
	
	// �ݹ�ʵ�� ��̬������
	public static long terribleJump(int stageNum) {
		return stageNum>2 ? 2*terribleJump(stageNum-1) : stageNum;
	}

}
