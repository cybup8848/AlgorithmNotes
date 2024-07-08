/*************************************************************************
    > File Name: question12.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri Jun 14 22:50:59 2024
 ************************************************************************/




public class question12{

	public static int maxScore(int[][] arr){
		if(arr==null||arr.length==0){
			return 0;
		}

		int row=arr.length;
		int col=arr[0].length;
		int max=0;
		for(int i=0;i<row;i++){
			int score=process(arr,row,col,i,0,1,0);
			max=Math.max(max,score);
		}

		return max;
	}

	public static int process(int[][] arr,int row,int col,int pr,int pc,int cn,int sum){
		if(sum<0||pr==row||pr<0||pc==col){
			return 0;
		}

		int rightUp=rightUp=process(arr,row,col,pr-1,pc+1,cn,sum+arr[pr][pc]);
		int right=process(arr,row,col,pr,pc+1,cn,sum+arr[pr][pc]);
		int rightDown=process(arr,row,col,pr+1,pc+1,cn,sum+arr[pr][pc]);
		int max1=Math.max(right,Math.max(rightUp,rightDown))+arr[pr][pc];
		
		int max2=0;
		if(arr[pr][pc]<0&&cn>0){
			rightUp=process(arr,row,col,pr-1,pc+1,cn-1,sum-arr[pr][pc]);
			right=process(arr,row,col,pr,pc+1,cn-1,sum-arr[pr][pc]);
			rightDown=process(arr,row,col,pr+1,pc+1,cn-1,sum-arr[pr][pc]);
			max2=Math.max(right,Math.max(rightUp,rightDown))+Math.abs(arr[pr][pc]);
		}
		
		return Math.max(max1,max2); 
	}
	

	//.动态规划的错误实现
	public static int maxScoreDP(int[][] arr){
		if(arr==null||arr.length==0){
			return 0;
		}


		int row=arr.length;
		int col=arr[0].length;
		int ceng=2;
		int[][][] result=new int[row+1][col+1][ceng];
		
		// 设置初始状态
		// result[i][j][1]：代表从(i,j)位置有超能力，能获取的最大分数
		// result[i][j][0]：代表从(i,j)位置没有超能力，能获取的最大分数
		
		for(int j=col-1;j>=0;--j){
			for(int i=0;i<row;i++){
				for(int k=0;k<ceng;k++){
					int rightUp=i-1>=0?result[i-1][j+1][k]:Integer.MIN_VALUE;
					int right=result[i][j+1][k];
					int rightDown=result[i+1][j+1][k];
					
					result[i][j][k]=Math.max(right,Math.max(rightUp,rightDown))+arr[i][j];
					
					if(arr[i][j]<0&&k>0){
						rightUp=i-1>=0?result[i-1][j+1][k-1]:Integer.MIN_VALUE;
						right=result[i][j+1][k-1];
						rightDown=result[i+1][j+1][k-1];

						int max=Math.max(right,Math.max(rightUp,rightDown))-arr[i][j];
						result[i][j][k]=Math.max(max,result[i][j][k]);
					}

					if(result[i][j][k]<0){
						result[i][j][k]=Integer.MIN_VALUE;
					}
				}
			}
		}

		int res=0;
		for(int i=0;i<row;i++){
			res=Math.max(res,result[i][0][1]);
		}
		
		return res;
	}

	//使用二维表格，优化空间
	public static int maxScoreDP1(int[][] arr){
		if(arr==null||arr.length==0){
			return 0;
		}

		int row=arr.length;
		int col=arr[0].length;
		int ceng=2;
		int[][] result=new int[row+1][ceng];
		for(int j=col-1;j>=0;--j){
			for(int k=1;k>=0;--k){//因为1层需要0层的数据，所以0层的数据后更新
				int tmp=Integer.MIN_VALUE;
				for(int i=0;i<row;i++){
					int rightUp=tmp;
					int right=result[i][k];
					int rightDown=result[i+1][k];
					
					tmp=result[i][k];
					result[i][k]=Math.max(right,Math.max(rightUp,rightDown))+arr[i][j];
					
					if(arr[i][j]<0&&k>0){
						rightUp=i>0?result[i][k-1]:Integer.MIN_VALUE;
						right=result[i][k-1];
						rightDown=result[i+1][k-1];
						
						int max=Math.max(right,Math.max(rightUp,rightDown))-arr[i][j];
						
						result[i][k]=Math.max(result[i][k],max);
					}

					if(result[i][k]<0){
						result[i][k]=Integer.MIN_VALUE;
					}
				}
			}
			
			
		}
		
		int res=Integer.MIN_VALUE;
		for(int i=0;i<row;i++){
			res=Math.max(res,result[i][1]);
		}
		
		return res;
	}


	//左程云实现
	public static int walk1(int[][] matrix){
		if(matrix==null||matrix.length==0||matrix[0].length==0){
			return 0;
		}

		int res=Integer.MIN_VALUE;
		int row=matrix.length;
		int col=matrix[0].length;
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				int[] ans=process1(matrix,i,j);
				res=Math.max(res,Math.max(ans[0],ans[1]));
			}
		}

		return res;
	}
	
	//从左侧到达(i,j)的旅程中
	// 0) 在没有使用过能力的情况下，返回路径最大和
	// 1) 在使用过能力的情况下，返回路径最大和
	public static int[] process1(int[][] m,int i,int j){
		if(j==0){// (i,j) 就是最左侧的位置
			return new int[]{
				m[i][j],-m[i][j]
			};
		}

		// (i,j)的左侧有之前的路
		// 第一条路
		int[] preAns=process1(m,i,j-1);
		int preUnuse=preAns[0];
		int preUse=preAns[1];

		if(i-1>=0){
			preAns=process1(m,i-1,j-1);
			preUnuse=Math.max(preUnuse,preAns[0]);
			preUse=Math.max(preUse,preAns[1]);
		}

		if(i+1<m.length){
			preAns=process1(m,i+1,j-1);
			preUnuse=Math.max(preUnuse,preAns[0]);
			preUse=Math.max(preUse,preAns[1]);
		}

		int unuse=-1;
		int use=-1;
		if(preUnuse>=0){
			unuse=preUnuse+m[i][j];
			use=preUnuse-m[i][j];
		}

		if(preUse>=0){
			use=Math.max(use,preUse+m[i][j]);
		}
		
		return new int[]{unuse,use};
	}

	
	//动态规划版本
	public static int walk2(int[][] matrix){
		if(matrix==null||matrix.length==0||matrix[0].length==0){
			return 0;
		}
		
		Info[][] infos=process2(matrix);

		int res=Integer.MIN_VALUE;
		int row=matrix.length;
		int col=matrix[0].length;
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				res=Math.max(res,Math.max(infos[i][j].noMax,infos[i][j].yesMax));
			}
		}
		
		return res;
	}

	public static Info[][] process2(int[][] matrix){
		int row=matrix.length;
		int col=matrix[0].length;
		
		Info[][] infos=new Info[row][col];
		for(int i=0;i<row;i++){
			infos[i][0]=new Info(matrix[i][0],-matrix[i][0]);
		}

		for(int j=1;j<col;++j){
			for(int i=0;i<row;++i){
				int preNo=infos[i][j-1].noMax;
				int preYes=infos[i][j-1].yesMax;
				
				if(i-1>=0){
					preNo=Math.max(preNo,infos[i-1][j-1].noMax);
					preYes=Math.max(preYes,infos[i-1][j-1].yesMax);
				}

				if(i+1<row){
					preNo=Math.max(preNo,infos[i+1][j-1].noMax);
					preYes=Math.max(preYes,infos[i+1][j-1].yesMax);
				}

				int no=-1;
				int yes=-1;
				if(preNo>=0){
					no=preNo+matrix[i][j];
					yes=preNo-matrix[i][j];
				}
				if(preYes>=0){
					yes=Math.max(yes,preYes+matrix[i][j]);
				}

				infos[i][j]=new Info(no,yes);
			}
		}

		return infos;
	}
	

	//蛇在任何一个位置都可能取得最大值
	public static int snake(int[][] matrix){
		int ans=Integer.MIN_VALUE;
		for(int row=0;row<matrix.length;row++){
			for(int col=0;col<matrix[0].length;col++){
				Info cur=f(matrix,row,col);
				ans=Math.max(ans,Math.max(cur.noMax,cur.yesMax));
			}
		}

		return ans;
	}

	//process1实际上就返回两个值
	public static class Info{
		public int noMax;//不使用超能力，获取的路径最大值
		public int yesMax;//使用超能力，获取的路径最大值
						  
		public Info(int no,int yes){
			noMax=no;
			yesMax=yes;
		}
	}
	
	//从最左侧出发（具体位置不关心），当前到达（row，col）
	//在这个旅程中，
	//no，一次能力也不用，能达到的最大路径和（如果是负数，表示没有该答案，表示从最左侧位置不能到达(row,col)位置）
	//yes，使用了一次超能力，能达到的最大路径和（如果是负数，表示没有该答案，表示从最左侧位置不能到达(row,col)位置）
	public static Info f(int[][] matrix,int row,int col){
		if(col==0){
			return new Info(matrix[row][col],-matrix[row][col]);
		}

		//没有在最左列
		int preNo=-1;//我之前的旅程中，一次能力也没用，能达到的最大路径和
		int preYes=-1;//我之前的旅程中，用过一次能力，能达到的最大路径和

		if(row>0){
			Info leftUp=f(matrix,row-1,col-1);
			if(leftUp.noMax>=0){
				preNo=leftUp.noMax;
			}
			if(leftUp.yesMax>=0){
				preYes=leftUp.yesMax;
			}
		}

		//p2
		Info left=f(matrix,row,col-1);
		if(left.noMax>=0){
			preNo=Math.max(preNo,left.noMax);
		}
		if(left.yesMax>=0){
			preYes=Math.max(preYes,left.yesMax);
		}

		//p3
		if(row<matrix.length-1){
			Info leftDown=f(matrix,row+1,col-1);
			if(leftDown.noMax>=0){
				preNo=Math.max(preNo,leftDown.noMax);
			}
			if(leftDown.yesMax>=0){
				preYes=Math.max(preYes,leftDown.yesMax);
			}
		}
		
		int no=-1;
		int yes=-1;
		if(preNo>0){
			no=preNo+matrix[row][col];
			yes=preNo-matrix[row][col];
		}

		if(preYes>=0){
			yes=Math.max(yes,preYes+matrix[row][col]);
		}


		return new Info(no,yes);
	}


	//加缓存
	public static int snake1(int[][] matrix){
		if(matrix==null||matrix.length==0||matrix[0].length==0){
			return 0;
		}
		
		int row=matrix.length;
		int col=matrix[0].length;

		int res=Integer.MIN_VALUE;
		
		Info[][] caches=new Info[row][col];
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				Info cur=f1(matrix,i,j,caches);
				res=Math.max(res,Math.max(cur.noMax,cur.yesMax));
			}
		}

		return res;
	}
	public static Info f1(int[][] matrix,int row,int col,Info[][] caches){
		if(caches[row][col]!=null){
			return caches[row][col];
		}
		
		if(col==0){
			caches[row][col]=new Info(matrix[row][col],-matrix[row][col]);
			return caches[row][col];
		}

		//没有在最左列
		int preNo=-1;//我之前的旅程中，一次能力也没用，能达到的最大路径和
		int preYes=-1;//我之前的旅程中，用过一次能力，能达到的最大路径和

		if(row>0){
			Info leftUp=f1(matrix,row-1,col-1,caches);
			if(leftUp.noMax>=0){
				preNo=leftUp.noMax;
			}
			if(leftUp.yesMax>=0){
				preYes=leftUp.yesMax;
			}
		}

		//p2
		Info left=f1(matrix,row,col-1,caches);
		if(left.noMax>=0){
			preNo=Math.max(preNo,left.noMax);
		}
		if(left.yesMax>=0){
			preYes=Math.max(preYes,left.yesMax);
		}

		//p3
		if(row<matrix.length-1){
			Info leftDown=f1(matrix,row+1,col-1,caches);
			if(leftDown.noMax>=0){
				preNo=Math.max(preNo,leftDown.noMax);
			}
			if(leftDown.yesMax>=0){
				preYes=Math.max(preYes,leftDown.yesMax);
			}
		}
		
		int no=-1;
		int yes=-1;
		if(preNo>0){
			no=preNo+matrix[row][col];
			yes=preNo-matrix[row][col];
		}

		if(preYes>=0){
			yes=Math.max(yes,preYes+matrix[row][col]);
		}

		caches[row][col]=new Info(no,yes);
		return caches[row][col];
	}

	


	public static void main(String[] args){
		int[][] arr={
			{
				1,-4,10
			},
			{
				3,-2,-1
			},
			{
				2,-1,0
			},
			{
				0,5,-2
			}
		};	
		System.out.println(walk1(arr));
		System.out.println(walk2(arr));
		System.out.println(snake(arr));
		System.out.println(snake1(arr));

		int[][] arr1={
			{
				1,-4,10
			},
			{
				3,-2,100
			},
			{
				2,-200,-2
			},
			{
				0,5,1000
			}
		};
		System.out.println(walk1(arr1));
		System.out.println(walk2(arr1));
		System.out.println(snake(arr1));
		System.out.println(snake1(arr1));


		int[][] arr2={
			{
				-100,-1000,1000000
			},
			{
				-200,-2000,2000000
			},
			{
				-300,-3000,3000000
			}
		};
		System.out.println(walk1(arr2));
		System.out.println(walk2(arr2));
		System.out.println(snake(arr2));
		System.out.println(snake1(arr2));



		System.out.println("hello world");
	}
}





