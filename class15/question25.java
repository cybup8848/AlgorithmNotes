/*************************************************************************
    > File Name: question25.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Mon May 13 18:22:51 2024
 ************************************************************************/




public class question25{

	public static int minRecur(int[][] arr){
		if(arr==null){
			return 0;
		}
		int row=arr.length-1;
		int col=arr[0].length-1;
		return processRecur(arr,0,0,row,col);
	}
	public static int processRecur(int[][] arr,int i,int j,int row,int col){
		if(i==row&&j==col){
			return arr[i][j];
		}
		
		int right=j+1<=col?processRecur(arr,i,j+1,row,col):processRecur(arr,i+1,j,row,col);
		int down=i+1<=row?processRecur(arr,i+1,j,row,col):processRecur(arr,i,j+1,row,col);

		return Math.min(down,right)+arr[i][j];
	}

	public static int minDp(int[][] arr){
		if(arr==null){
			return 0;
		}

		int row=arr.length;
		int col=arr[0].length;

		int[][] dp=new int[row][col];

		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				int res=arr[i][j];
				int left=res+(j-1>=0?dp[i][j-1]:0);
				int up=res+(i-1>=0?dp[i-1][j]:0);
				if(j-1>=0&&i-1>=0){
					res+=Math.min(dp[i][j-1],dp[i-1][j]);
				} else if(j-1>=0){
					res+=dp[i][j-1];
				} else if(i-1>=0){
					res+=dp[i-1][j];
				}
				dp[i][j]=res;
			}
		}
		return dp[row-1][col-1];
	}

	//动态规划的空间压缩
	public static int minDp1(int[][] arr){
		if(arr==null){
			return 0;
		}

		int row=arr.length;
		int col=arr[0].length;
		int[] res=new int[col];
		
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				if(i==0&&j==0){
					res[0]=arr[0][0];
				} else if(i==0){
					res[j]=res[j-1]+arr[0][j];
				} else if(j==0){
					res[0]=res[0]+arr[i][0];
				} else {
					res[j]=Math.min(res[j-1],res[j])+arr[i][j];
				}
			}
		}

		return res[col-1];
	}





	public static void main(String[] args){
		int[][] arr={
			{
				1,2,3,4,5
			},
			{
				6,7,8,9,10
			},
			{
				11,12,13,14,15
			}
		};
		
		System.out.println(minRecur(arr));
		System.out.println(minDp(arr));
		System.out.println(minDp1(arr));

		System.out.println("hello world");
	}
}
