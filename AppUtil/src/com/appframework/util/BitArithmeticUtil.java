package com.appframework.util;

public class BitArithmeticUtil {
	
	public static BitArithmeticUtil instance= null;
	
	private BitArithmeticUtil(){}
	public static BitArithmeticUtil getInstance() {
		if (instance == null){
			synchronized (BitArithmeticUtil.class) {
				if (instance == null){
					instance = new BitArithmeticUtil();
				}
			}
		}
		return instance;
	}
	
//	判断int型变量a是奇数还是偶数    
	public static boolean isEvenNumber(int number){
		boolean bflag = false;
		if ((number&1) == 0){ //偶数
			bflag = true;
		}
//		if ((number&1) == 1){ //奇数
//			bflag = false;
//		}
		return bflag;
	}
	
//	求平均值
	public static float average(int n1, int n2){
		float result = 0.f;
		System.out.println(" n = " + (n1&n2));
		System.out.println(" n1 = " + (n1^n2));
		System.out.println(" n2 = " + ((n1^n2)>>1));
		result = (n1&n2)+((n1^n2)>>1);
		
		return result;
	}
	
//	对于一个大于0的整数，判断它是不是2的几次方
	
	public static boolean judgePower(int number){
		boolean bflag = false;
		bflag = ((number&(number-1)) == 0)&&(number !=0);
		return bflag;
	}
	
//	比如有两个int类型变量x、y,要求两者数字交换，位运算的实现方法：性能绝对高效
//	原因是异或运算的逆运算就是自己，也就是一个数对一个数异或运算两次就是其本身。
	public static void switchNumber(int n1, int n2){
		System.out.println("switch before n1 = "+n1 +", n2=" + n2);
		n1^=n2;
		n2^=n1;
		n1^=n2;
		System.out.println("switch after n1 = "+n1 +", n2=" + n2);
	}
//	求绝对值
	public static int abs(int n1){
		int n2;
		n2 = n1 >> 31;
		
		return (n1^n2) - n2; //(n1 + n2)^n2
	}
	
//	取模运算，采用位运算实现
//	a % (2^n) 等价于 a & (2^n - 1) 
//	乘法运算   采用位运算实现
//	 a * (2^n) 等价于 a << n
//	除法运算转化成位运算
//	  a / (2^n) 等价于 a>> n 
//	 求相反数
//    (~x+1) 
//  a % 2 等价于 a & 1 
	
//	下面以经典的二分求幂做一个例子：
	public static int Power(int a, int n, int mod) // cal (a^n)%mod
	{
		int ans = 1;
		while (n > 0) {
			if ((n & 1) == 0) {
				ans *= a;
				n--;
			} else {
				a *= a;
				n >>= 1;
			}
			ans %= mod;
		}
		return ans;
	}
	
//	3. 位运算的其他应用
//	(1) 取int型变量a的第k位 (k=0,1,2……sizeof(int))
//	a>>k&1
//	(2) 将int型变量a的第k位清0
//	a=a&~(1<<k)
//	(3) 将int型变量a的第k位置1
//	a=a|(1<<k)
//	(4) int型变量循环左移k次
//	a=a<<k|a>>16-k   (设sizeof(int)=16)
//	(5) int型变量a循环右移k次
//	a=a>>k|a<<16-k   (设sizeof(int)=16)
//	(6) 实现最低n位为1，其余位为0的位串信息:
//	~（~0 << n）
//	(7)截取变量x自p位开始的右边n位的信息:
//	(x >> (1+p-n)) & ~(~0 << n)
//	(8)截取old变量第row位，并将该位信息装配到变量new的第15-k位
//	new |= ((old >> row) & 1) << (15 – k)
//	(9)设s不等于全0，代码寻找最右边为1的位的序号j:
//	for(j = 0; ((1 << j) & s) == 0; j++) ;

}
