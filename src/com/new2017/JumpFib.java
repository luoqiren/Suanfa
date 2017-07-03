package com.new2017;

/**
 * 跳台阶问题 + 变态跳台阶问题 解法（动态规划递归 ）
 * 扩展阅读：这个数列从第3项开始，每一项都等于前两项之和
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
	 * 题目描述： 一个台阶总共有n级，如果一次可以跳1级，也可以跳2级。求总共有多少总跳法，并分析算法的时间复杂度。
	 * 通过题目的描述，可以很清晰地看到，这就是一个Fibonacci数列。
	 *         | 1 n=1
	 *  F(n) = | 2 n=2
	 *         | F(n-1) +F(n-2)
	 */
	// 递归实现 1
	public static long solutionOne(int stageNum) {
		// 定义递归出口
		// 这是最低级的做法，耗费的时间是输入规模的指数级别的。可以加入计算缓存来提高递归速度。
		if (stageNum <= 0) {
			return 0;
		} else if (1 == stageNum) {
			return 1;
		} else if (2 == stageNum) {
			return 2;
		}
		return solutionOne(stageNum - 1) + solutionOne(stageNum - 2);
	}

	// 递归实现 2
	public static long solutionTwo(int stageNum) {
		// 自顶向下的动态规划
		long[] counter = new long[101];
		if (0 != counter[stageNum]) {
			return counter[stageNum];
		}
		// 定义递归出口
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
	 * 二、变态跳台阶问题 题目描述： 一个台阶总共有n级，如果一次可以跳1级，也可以跳2级……也可以跳n级。求总共有多少总跳法，并分析算法的时间复杂度.
	 * 
	 * 题目描述： 一个台阶总共有n级，如果一次可以跳1级，也可以跳2级……也可以跳n级。求总共有多少总跳法，并分析算法的时间复杂度。
	 * 
	 * 分析：用Fib(n)表示跳上n阶台阶的跳法数。如果按照定义，Fib(0)肯定需要为0，否则没有意义。但是我们设定Fib(0) = 1；n =
	 * 0是特殊情况，通过下面的分析就会知道， 强制令Fib(0) = 1很有好处。ps: Fib(0)等于几都不影响我们解题，但是会影响我们下面的分析理解。 
	 * 当n = 1 时， 只有一种跳法，即1阶跳：Fib(1) = 1; 当n =
	 * 2 时， 有两种跳的方式，一阶跳和二阶跳：Fib(2) = 2; 到这里为止，和普通跳台阶是一样的。 当n = 3
	 * 时，有三种跳的方式，第一次跳出一阶后，对应Fib(3-1)种跳法； 第一次跳出二阶后，对应Fib(3-2)种跳法；
	 * 第一次跳出三阶后，只有这一种跳法。Fib(3) = Fib(2) + Fib(1)+ 1 = Fib(2) + Fib(1) + Fib(0) =
	 * 4; 当n =
	 * 4时，有四种方式：第一次跳出一阶，对应Fib(4-1)种跳法；第一次跳出二阶，对应Fib(4-2)种跳法；第一次跳出三阶，对应Fib(4-3)
	 * 种跳法；第一次跳出四阶，只有这一种跳法。 所以，Fib(4) = Fib(4-1) + Fib(4-2) + Fib(4-3) + 1 =
	 * Fib(4-1) + Fib(4-2) + Fib(4-3) + Fib(4-4) 种跳法。 
	 * -----------解题主思路------start----------------
	 * 当n = n 时，共有n种跳的方式，第一次跳出一阶后，后面还有Fib(n-1)中跳法；
	 * 第一次跳出二阶后，后面还有Fib(n-2)中跳法.......................... 第一次跳出n阶后，后面还有Fib(n-n)中跳法。
	 * -----------解题主思路-------end-----------------
	 * Fib(n) = Fib(n-1)+Fib(n-2)+Fib(n-3)+..........+Fib(n-n) =
	 * Fib(0)+Fib(1)+Fib(2)+.......+Fib(n-1)。 通过上述分析，我们就得到了通项公式： 
	 * Fib(n) = Fib(0)+Fib(1)+Fib(2)+.......+ Fib(n-2) + Fib(n-1) 因此，有
	 * Fib(n-1)=Fib(0)+Fib(1)+Fib(2)+.......+Fib(n-2) 两式相减得：Fib(n)-Fib(n-1) =
	 * Fib(n-1) =====》 Fib(n) = 2*Fib(n-1) n >= 3 这就是我们需要的递推公式：Fib(n) = 2*Fib(n-1) n >= 3
	 */
	
	// 递归实现 变态跳问题
	public static long terribleJump(int stageNum) {
		return stageNum>2 ? 2*terribleJump(stageNum-1) : stageNum;
	}

}
