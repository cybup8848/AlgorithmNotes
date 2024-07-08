/*************************************************************************
    > File Name: BankerAlgorithm.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed Jan 31 09:31:35 2024
 ************************************************************************/
import java.util.Scanner;
public class BankerAlgorithm{
	public static int[] available;//记录系统中各类资源的当前可利用项目
	
	public static int[][] maxs;//记录每个进程对各类资源的最大需求量，[i][j]：进程i对进程j所需要的最大资源
			
	public static int[][] allocation;//记录每个进程对各类资源当前的占用量，[i][j]：进程i对资源j的当前占有量
	
	public static int[][] need;//记录每个进程对各类资源尚需要的数目，[i][j]：进程i对资源j还需要多少
	
	//int[][] request;//记录每个进程当前对各类资源的申请量，是银行家算法的入口参数，[i][j]：进程i对资源j的请求量
	public static int[] request;//请求向量当前进程对各类资源的申请量，算法的入口参数

	public static int p;//进程数
	public static int r;//资源种类
	
	public static void infInput(){
		Scanner input=new Scanner(System.in);
		System.out.println("请输入进程数：");
		p=input.nextInt();

		System.out.println("请输入资源种类数：");
		r=input.nextInt();

		maxs=new int[p][r];
		System.out.println("请输入最大需求矩阵max：");
		for(int i=0;i<p;i++){
			for(int j=0;j<r;j++){
				maxs[i][j]=input.nextInt();
			}
		}

		allocation=new int[p][r];
		System.out.println("请输入分配矩阵allocation：");
		for(int i=0;i<p;i++){
			for(int j=0;j<r;j++){
				allocation[i][j]=input.nextInt();
			}
		}
		
		need=new int[p][r];
		System.out.println("请输入需求矩阵need：");
		for(int i=0;i<p;i++){
			for(int j=0;j<r;j++){
				need[i][j]=input.nextInt();
			}
		}
		
		System.out.println("请输入可用资源向量available：");
		available=new int[r];
		for(int i=0;i<r;i++){
			available[i]=input.nextInt();
		}

		//初始化request
		request=new int[r];
	}

	//比较函数
	//比较进程m中的元素全大于n中的元素返回1，否则返回0
	public static boolean compare(int[] m,int[] n){
		for(int i=0;i<r;i++){
			if(m[i]<n[i]){
				return false;
			}
		}
		return true;
	}

	//安全性检验函数，检测是否存在安全序列
	public static boolean securityCheck(){
		boolean[] finish=new boolean[p];
		int[] work=new int[r];

		for(int i=0;i<p;i++){
			finish[i]=false;
		}

		for(int i=0;i<r;i++){
			work[i]=available[i];//available的替代
		}

		System.out.println("分配序列：");
		System.out.println("allocation		nedd		available");
		for(int k=0;k<p;k++){
			boolean flag=false;
			for(int i=0;i<p;i++){
				if(finish[i]==true){
					continue;
				} else {
					if(compare(work,need[i])){//available>=need
						finish[i]=true;
						System.out.print("\n进程"+(i+1)+"\t\t");
						flag=true;
						for(int j=0;j<r;j++){
							System.out.print(allocation[i][j]+"\t");
						}
						System.out.print("\t\t");
						for(int j=0;j<r;j++){
							System.out.print(need[i][j]+"\t");
						}
						System.out.print("\t\t");
						for(int j=0;j<r;j++){
							System.out.print(work[j]+allocation[i][j]+"\t");
						}
						for(int j=0;j<r;j++){
							work[j]+=allocation[i][j];
						}
						break;
					}
				}
				if(flag){
					break;
				}
			}
		}
		
		System.out.println();
		for(int i=0;i<p;i++){
			if(!finish[i]){
				return false;//不安全序列
			}
		}
		return true;
	}

	//申请进程后的安全性检验函数
	public static void bankerAlgorithm(int n){
		if(compare(available,request)&&compare(need[n-1],request)){//available>=request，并且 need>=request
			for(int j=0;j<r;j++){
				allocation[n-1][j]=allocation[n-1][j]+request[j];
				need[n-1][j]=need[n-1][j]-request[j];
				available[j]=available[j]-request[j];
			}

			if(securityCheck()){
				System.out.println("允许"+n+"进程申请资源");
			} else {
				System.out.println("不允许"+n+"进程申请资源");
				for(int j=0;j<r;j++){
					allocation[n-1][j]-=request[j];
					need[n-1][j]+=request[j];
					available[j]+=request[j];
				}
			}
		} else {
			System.out.println("申请资源量越界");
		}
	}
	
	public static void main(String[] args){
		infInput();//输入函数

		if(securityCheck()){
			System.out.println("存在安全序列，初始状态安全");
		} else {
			System.out.println("不存在安全序列，初始状态不安全");
		}

		Scanner scanner=new Scanner(System.in);
		System.out.println("请输入发出请求向量request的进程编号：");
		int n=scanner.nextInt();

		System.out.println("请输入请求向量request：");
		for(int i=0;i<r;i++){
			request[i]=scanner.nextInt();
		}
		
		bankerAlgorithm(n);
		
		System.out.println("我喜欢坂井泉水");
	}
}
