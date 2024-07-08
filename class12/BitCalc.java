/*************************************************************************
    > File Name: BitCalc.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed Mar  6 14:41:39 2024
 ************************************************************************/
public class BitCalc{

	//给定两个有符号32位整数a和b，返回a和b中较大的。要求：不用做任何比较判断
	//要熟悉位运算的技巧
	
	//请保证参数n，不是1就是0的情况下
	// 1-->0
	// 0-->1
	public static int flip(int n){
		return n^1;
	}
	
	//n是非负数，返回1
	//n是负数，返回0
	public static int sign(int n){
		return flip((n>>31)&1);
	}

	//存在的缺陷：int c=a-b，可能会溢出
	public static int getMax1(int a,int b){
		int c=a-b;
		int srcA=sign(c);//a-b为非负，srcA为1；a-b为负，srcA为0
		int srcB=flip(srcA);
		//srcA为0，srcB为1；srcA为1，srcB为0
		return a*srcA+b*srcB;
	}
	//两个条件互斥，可以实现if-else

	//下面是没缺陷的方法，考虑了a-b溢出的情况
	public static int getMax2(int a,int b){
		int c=a-b;
		int sa=sign(a);
		int sb=sign(b);
		int sc=sign(c);

		int difSab=sa^sb;//a和b的符号不一样，为1；一样为0
		int sameSab=flip(difSab);

		int returnA=difSab*sa+sameSab*sc;
		int returnB=flip(returnA);
		return a*returnA+b*returnB;
	}

	//	32位正数是否是2的幂
	//	自己实现
	public static boolean is2(int x){
		int count=0;
		int num=x;
		while(num!=1){
			num>>=1;
			count++;
		}
		return x==(1<<count);
	}

	//二进制中只有一个位上是1
	public static boolean is2_1(int num){
		int flag=0;
		while(num!=0){
			flag=num&1;
			if(flag==1){
				break;
			}
			num=num>>1;
		}
		return (flag==1)&&(num==0);
	}

	public static boolean is2_2(int num){
		int flag=0;
		while(num!=0&&(flag==0)){
			flag=num&1;
			num<<=1;
		}
		return (flag==1)&&(num==0);
	}

	//左程云实现
	//拿到最右侧的1
	public static boolean is2_3(int num){
		return num==(num&(~num+1));//把最右侧的1拿出来
	}

	public static boolean is2_4(int num){
		return (num&(num-1))==0;// num-1 会把唯一的1打散掉   100    011
	}
	
	public static boolean is2Power(int n){
		return (n&(n-1))==0;
	}
	
	//32位正数是否是4的幂次
	//首先只有1个1，并且1在偶数位上(索引从0开始算)
	public static boolean is4(int x){
		int flag=0;
		int index=-1;
		while((x!=0)&&(flag==0)){
			flag=x&1;
			index++;
			x>>=1;  
		}
		return (x==0)&&(index%2==0);
	}

	//首先判断是否是2的幂次，然后将x&(0101......01)是否为0，不为0则是4的幂次，为0则不是4的幂次
	public static boolean is4_1(int x){
		if(is2_3(x)){
			if((x&(0b01010101010101010101010101010101))!=0){
				return true;
			}
		}
		return false;
	}

	//不使用算术运算符，实现加法
	public static int addNoOperator(int a,int b){
		int infoNoCarry=0;
		int infoCarry=1;
		while(infoCarry!=0){
			infoNoCarry=a^b;
			infoCarry=(a&b)<<1;
			a=infoNoCarry;
			b=infoCarry;
		}
		return infoNoCarry;
	}

	//左程云实现
	public static int add(int a,int b){
		int sum=a;
		while(b!=0){
			sum=a^b;
			b=(a&b)<<1;
			a=sum;
		}
		return sum;
	}

	//不使用算术运算符，实现减法
	public static int negNum(int n){
		return add(~n,1);
	}
	public static int minus(int a,int b){
		return add(a,negNum(b));
	}

	//不使用算术运算符，实现乘法
	public static int multi(int a,int b){
		int res=0;
		while(b!=0){
			if((b&1)!=0){
				res=add(res,b);
			}
			a<<=1;
			b>>>=1;
		}
		return res;
	}

	//不使用算术运算符，实现除法
	public static boolean isNeg(int n){
		return n<0;
	}
	public static int div(int a,int b){
		int x=isNeg(a)?negNum(a):a;
		int y=isNeg(b)?negNum(b):b;
		int res=0;
		for(int i=31;i>-1;i=minus(i,1)){
			if((x>>i)>=y){//x右移、y左移，效果一样，y左移可能溢出
				res|=(1<<i);
				x=minus(x,y<<i);
			}
		}
		return isNeg(a)^isNeg(b)?negNum(res):res;
	}

	public static int divide(int a,int b){
		if(b==0){
			throw new RuntimeException("divisor is 0");
		}
		if(a==Integer.MIN_VALUE && b==Integer.MIN_VALUE){
			return 1;
		} else if(b==Integer.MIN_VALUE){
			return 0;
		} else if(a==Integer.MIN_VALUE){
			int res=div(add(a,1),b);
			return add(res,div(minus(a,multi(res,b)),b));
		} else{
			return div(a,b);
		}
	}

	//左程云实现
	public static boolean is4Power(int n){
		return (n&(n-1))==0&&(n&0x55555555)!=0;
	}

	public static void main(String[] args){
		int a=-16;
		int b=1;
		System.out.println(getMax1(a,b));
		System.out.println(getMax2(a,b));

		a=2147483647;
		b=-2147480000;
		System.out.println(getMax1(a,b));//出错
		System.out.println(getMax2(a,b));


		System.out.println("hello world");
	}
}
