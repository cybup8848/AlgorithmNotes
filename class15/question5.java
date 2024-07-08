/*************************************************************************
    > File Name: question5.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue May  7 18:31:35 2024
 ************************************************************************/
public class question5{

	//等概率返回1~5
	public static int f1(){
		return (int)(Math.random()*5)+1;
	}
	
	//等概率返回01和1
	public static int r01(){
		int r=f1();
		while(r==5){
			r=f1();
		}
		
		int res=0;
		if(r==3||r==4){
			res=1;
		}
		return res;
	}

	//等概率返回1~7
	public static int g1(){
		int res=0;
		do{
			res=(r01()<<2)+(r01()<<1)+r01();
		}while(res==0);
		return res;
	}

	
	public static int f2(){
		return (int)(Math.random()*2)==0?'a':'b';
	}

	public static int g2(){
		return f2()+1;
	}

	//自己实现
	//概率p返回0，1-p概率返回1
	public static int f3(double p){
		return Math.random()<p?0:1;
	}

	public static int r3(double p){
		int num=0;
		do{
			num=f3(p)<<1+f3(p);
		}while(num==0||num==3);
		return num-1;
	}

	public static int g3(double p){
		return r3(p);
	}
	
	//左程云实现
	public static int rand01p(){
		//you can change p to what you like,but it must be (0,1)
		double p=0.83;
		return Math.random()<p?0:1;
	}

	public static int rand01(){
		int num=0;
		do{
			num=rand01()<<1+rand01();
		}while(num==0||num==3);
		return num-1;
	}

	public static int frand(){
		return rand01();
	}
	
	
	public static void main(String[] args){




		System.out.println("hello world");
	}
}
