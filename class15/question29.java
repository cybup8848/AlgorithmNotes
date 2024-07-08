/*************************************************************************
    > File Name: question29.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue May 14 18:48:35 2024
 ************************************************************************/


public class question29{

	//递归	
	public static int satisfyStringNum(int n){
		if(n<2){
			return n;
		}


		return process(1,n-1);
	}

	public static int process(int pre,int cn){
		if(cn==0){
			return 1;
		}
		
		int res=0;
		if(pre==1){
			res=process(1,cn-1)+process(0,cn-1);
		} else {
			res=process(1,cn-1);
		}
		return res;
	}

	//动态规划
	public static int satisfyStringNum1(int n){
		if(n<1){
			return 0;
		}

		if(n==1){
			return 1;
		}

		if(n==2){
			return 2;
		}

		int first=1;
		int second=2;
		int res=0;
		for(int i=3;i<=n;i++){
			res=first+second;
			first=second;
			second=res;
		}
		return res;
	}

	//转换为求矩阵的n次幂
	public static int satisfyStringNum2(int n){
		if(n<1){
			return 0;
		}

		if(n<3){
			return n;
		}

		int[][] base={
			{
				1,1
			},
			{
				1,0
			}
		};

		int[][] res=matrixPower(base,n-2);
		return 2*res[0][0]+res[1][0];
	}

	public static int[][] matrixPower(int[][] m,int p){
		int row=m.length;
		int col=m[0].length;
		int[][] res=new int[row][col];
		for(int i=0;i<row;++i){
			res[i][i]=1;
		}

		int[][] tmp=m;
		for(;p!=0;p>>=1){
			if((p&1)!=0){
				res=matrixMulti(res,tmp);
			}
			tmp=matrixMulti(tmp,tmp);
		}
		
		return res;
	}

	public static int[][] matrixMulti(int[][] m1,int[][] m2){
		int m1Row=m1.length;
		int m1Col=m1[0].length;

		int m2Row=m2.length;
		int m2Col=m2[0].length;

		if(m1Col!=m2Row){
			return null;
		}

		int[][] res=new int[m1Row][m2Col];
		for(int i=0;i<m1Row;++i){
			for(int j=0;j<m2Col;++j){
				int sum=0;
				for(int k=0;k<m1Col;++k){
					sum+=m1[i][k]*m2[k][j];
				}
				res[i][j]=sum;
			}
		}
		return res;
	}


	public static void main(String[] args){
		for(int i=0;i<10;i++){
			System.out.println(satisfyStringNum(i)+"	"+satisfyStringNum1(i)+"	"+satisfyStringNum2(i));
		}



		
		

		System.out.println("hello world");
	}
}
