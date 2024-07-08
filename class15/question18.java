/*************************************************************************
    > File Name: question18.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri May 10 14:53:21 2024
 ************************************************************************/



public class question18{

	//自己实现
	public static void printBySpiral(int[][] arr){
		if(arr==null){
			return;
		}

		int flag=0;
		//0：向左    1：向下    2：向右    3：向上

		int row=arr.length;
		int col=arr[0].length;
		int cn=row*col;
		
		int x=0;
		int y=0;
		int ceng=0;
		for(int i=0;i<cn;i++){
			System.out.print(arr[x][y]+"	");
			if(flag==0){
				if(y+1<col-ceng){
					y+=1;
				}else{
					flag=1;
					x+=1;
				}
			}else if(flag==1){
				if(x+1<row-ceng){
					x+=1;
				} else {
					flag=2;
					y-=1;
				}
			} else if(flag==2){
				if(y-1>=ceng){
					y-=1;
				} else {
					flag=3;
					x-=1;
				}
			} else if(flag==3){
				if(x-1>=ceng+1){
					x-=1;
				} else {
					ceng+=1;
					flag=0;
					y+=1;
				}
			}
		}

		System.out.println("\n");
	}

	//左程云实现
	public static void spiralOrderPrint(int[][] matrix){
		int tR=0;
		int tC=0;

		int dR=matrix.length-1;
		int dC=matrix[0].length-1;
		
		while(tR<=dR&&tC<=dC){
			printEdge(matrix,tR++,tC++,dR--,dC--);
		}
	}

	public static void printEdge(int[][] m,int a,int b,int c,int d){
		if(a==c){
			for(int i=b;i<=d;i++){
				System.out.print(m[a][i]+"	");
			}
		} else if(b==d){
			for(int i=a;i<=c;i++){
				System.out.print(m[i][b]+"	");
			}
		} else {
			int curC=b;
			int curR=a;

			while(curC!=d){
				System.out.print(m[a][curC++]+"	");
			}

			while(curR!=c){
				System.out.print(m[curR++][d]+"	");
			}

			while(curC!=b){
				System.out.print(m[c][curC--]+"	");
			}
			
			while(curR!=a){
				System.out.print(m[curR--][b]+"	");
			}
		}

	}
	
	



	public static void main(String[] args){
		int[][] arr={
			{
				0,1,2,3
			},
			{
				4,5,6,7
			},
			{
				8,9,10,11
			}
		};
		printBySpiral(arr);
		spiralOrderPrint(arr);


		System.out.println("hello world");
	}
}
