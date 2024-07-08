/*************************************************************************
    > File Name: winner.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Mon May  6 20:14:51 2024
 ************************************************************************/
public class winner{

	//先手后手两只动物吃草，只能吃4的某次方份量的草
	//谁最先把草吃完，谁赢
	
	//n份青草放在一堆
	//先手、后手都决定聪明
	//string "先手" "后手"
	
	public static String winner1(int n){
		// o  1  2  3  4
		// 后 先 后 先 先
		
		if(n<5){
			return (n==0||n==2)?"后手":"先手";
		}

		//n>=5时
		int base=1;//先手决定吃的草

		//有问题
		while(base<=n){
			//当前一共n份草，先手吃掉的是base份，n-base是留给后手的草
			//母过程 先手 在子过程里是 后手
			if(winner1(n-base).equals("后手")){//子过程中的后手就是母过程中的先手
				return "先手";
			}

			if(base>n/4){//防止base*4之后溢出
				break;
			}
			base*=4;
		}
		return "后手";//尝试完，先手还不能赢，只能是后手赢
	}
	
	public static String winner2(int n){
		if(n%5==0||n%5==2){
			return "后手";
		}
		return "先手";
	}
	
	
	
	
	public static void main(String[] args){
		for(int i=0;i<50;i++){
			System.out.println(i+" : "+winner1(i));
		}
		
		
		System.out.println("hello world");
	}
	
	
}
