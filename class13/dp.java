/*************************************************************************
    > File Name: DP1.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed Mar 13 15:46:18 2024
 ************************************************************************/
public class dp{

	//马踏棋盘
	public static int horseToPos1(int x,int y,int a,int b,int sx,int sy,int k){
		if(k==0){
			return (a==sx&&b==sy)?1:0;
		}
		
		if(sx>x||sy>y||sx<1||sy<1){
			return 0;
		}
		
		int res=0;
		res+=horseToPos1(x,y,a,b,sx-1,sy+2,k-1);
		res+=horseToPos1(x,y,a,b,sx-1,sy-2,k-1);
		res+=horseToPos1(x,y,a,b,sx+1,sy+2,k-1);
		res+=horseToPos1(x,y,a,b,sx+1,sy-2,k-1);
		res+=horseToPos1(x,y,a,b,sx-2,sy+1,k-1);
		res+=horseToPos1(x,y,a,b,sx-2,sy-1,k-1);
		res+=horseToPos1(x,y,a,b,sx+2,sy+1,k-1);
		res+=horseToPos1(x,y,a,b,sx+2,sy-1,k-1);
		
		return res;//排除掉无效答案
	}

	//记忆搜索
	public static int horseToPos2(int x,int y,int a,int b,int sx,int sy,int k){
		int[][][] dp=new int[k+1][x][y];
		for(int i=0;i<k+1;i++){
			for(int row=0;row<x;row++){
				for(int col=0;col<y;col++){
					dp[i][row][col]=-1;
				}
			}
		}
		return process2(x,y,a,b,sx,sy,k,dp);
	}

	public static int process2(int x,int y,int a,int b,int sx,int sy,int k,int[][][] dp){
		if(sx>=x||sy>=y||sx<0||sy<0){
			return 0;
		}

		if(k==0){
			dp[k][sx][sy]=(a==sx&&b==sy?1:0);
			return dp[k][sx][sy];
		}	

		if(dp[k][sx][sy]!=-1){
			return dp[k][sx][sy];
		}
		
		int res=0;
		res+=process2(x,y,a,b,sx-1,sy+2,k-1,dp);
        res+=process2(x,y,a,b,sx-1,sy-2,k-1,dp);
        res+=process2(x,y,a,b,sx+1,sy+2,k-1,dp);
        res+=process2(x,y,a,b,sx+1,sy-2,k-1,dp);
		res+=process2(x,y,a,b,sx-2,sy+1,k-1,dp);
        res+=process2(x,y,a,b,sx-2,sy-1,k-1,dp);
        res+=process2(x,y,a,b,sx+2,sy+1,k-1,dp);
		res+=process2(x,y,a,b,sx+2,sy-1,k-1,dp);

        dp[k][sx][sy]=res;
		return res;//排除掉无效答案
	}

	//严格表结构，有三个可变参数，所以是三维表
	public static int horseToPos3(int x,int y,int a,int b,int sx,int sy,int k){
		int[][][] dp=new int[k+1][x][y];
		dp[0][a][b]=1;
		for(int i=1;i<k+1;i++){
			for(int j=0;j<x;j++){
				for(int m=0;m<y;m++){
					dp[i][j][m]+=(j-1>=0&&m+2<y?dp[i-1][j-1][m+2]:0);					
					dp[i][j][m]+=(j+1<x&&m+2<y?dp[i-1][j+1][m+2]:0);
					dp[i][j][m]+=(j-1>=0&&m-2>=0?dp[i-1][j-1][m-2]:0);
					dp[i][j][m]+=(j+1<x&&m-2>=0?dp[i-1][j+1][m-2]:0);
					
					dp[i][j][m]+=(j-2>=0&&m+1<y?dp[i-1][j-2][m+1]:0);
					dp[i][j][m]+=(j-2>=0&&m-1>=0?dp[i-1][j-2][m-1]:0);
					dp[i][j][m]+=(j+2<x&&m+1<y?dp[i-1][j+2][m+1]:0);
					dp[i][j][m]+=(j+2<x&&m-1>=0?dp[i-1][j+2][m-1]:0);
				}
			}
		}
		return dp[k][sx][sy];
	}

	//左程云实现
	public static int getWays(int x,int y,int k){
		return process(x,y,k);
	}

	//跳过了多少步，step
	//x，y在的位置
	//从(0,0)出发跳了step之后，来到(r,c)位置的方法数有多少种
	public static int process(int a,int b,int step){
		if(a<0||a>8||b<0||b>9){
			return 0;
		}
		if(step==0){
			return (a==0&&b==0)?1:0;
		}
		
		return process(a-1,b+2,step-1)
			    +process(a+1,b+2,step-1)
			    +process(a+2,b+1,step-1)
				+process(a+2,b-1,step-1)
				+process(a+1,b-2,step-1)
				+process(a-1,b-2,step-1)
				+process(a-2,b-1,step-1)
				+process(a-2,b+1,step-1);
	}

	public static int dpWays(int x,int y,int step){
		if(x<0||x>8||y<0||y>9||step<0){
			return 0;
		}

		int[][][] dp=new int[9][10][step+1];
		dp[0][0][0]=1;
		for(int h=1;h<=step;h++){
			for(int r=0;r<9;r++){
				for(int c=0;c<10;c++){
					dp[r][c][h]+=getValue(dp,r-1,c+2,h-1);
					dp[r][c][h]+=getValue(dp,r+1,c+2,h-1);
					dp[r][c][h]+=getValue(dp,r+2,c+1,h-1);
					dp[r][c][h]+=getValue(dp,r+2,c-1,h-1);
					dp[r][c][h]+=getValue(dp,r+1,c-2,h-1);
					dp[r][c][h]+=getValue(dp,r-1,c-2,h-1);
					dp[r][c][h]+=getValue(dp,r-2,c-1,h-1);
					dp[r][c][h]+=getValue(dp,r-2,c+1,h-1);
				}
			}
		}
		return dp[x][y][step];
	}
	
	public static int getValue(int[][][] dp,int row,int col,int step){
		if(row<0||row>8||col<0||col>9){
			return 0;
		}
		return dp[row][col][step];
	}
	

	public static double liveProb(int row,int col,int a,int b,int k){
		return Math.pow(0.25,k)*getPathNum(row,col,a,b,k);
	}
	
	public static double getPathNum(int row,int col,int a,int b,int k){
		if(a>=row||a<0||b>=col||b<0){
			return 0;
		}
		
		if(k==0){
			return 1;
		}

		return getPathNum(row,col,a-1,b,k-1)
				+getPathNum(row,col,a+1,b,k-1)
				+getPathNum(row,col,a,b+1,k-1)
				+getPathNum(row,col,a,b-1,k-1);
	}
	
	//左程云实现
	public static String bob1(int N,int M,int i,int j,int k){
		long all=(long)Math.pow(4,k);
		long live=process(N,M,i,j,k);
		long gcd=gcd(all,live);//求最大公约数
		return String.valueOf((live/gcd)+"/"+(all/gcd));//分数表达
	}
	public static long process(int N,int M,int row,int col,int rest){
		if(row<0||row==N||col<0||col==M){
			return 0;
		}
		if(rest==0){
			return 1;
		}
		return process(N,M,row-1,col,rest-1)
				+process(N,M,row+1,col,rest-1)
				+process(N,M,row,col-1,rest-1)
				+process(N,M,row,col+1,rest-1);
	}
	public static long gcd(long m,long n){
		return n==0?m:gcd(n,m%n);
	}

	
	public static double bob2(int N,int M,int a,int b,int k){
		if(a<0||a==M||b<0||b==M){
			return 0;
		}
		
		int[][][] dp=new int[k+1][N][M];
		for(int i=0;i<N;i++){
			for(int j=0;j<M;j++){
				dp[0][i][j]=1;
			}
		}
		for(int i=1;i<=k;i++){
			for(int j=0;j<N;j++){
				for(int m=0;m<M;m++){
					dp[i][j][m]=getValue2(dp,N,M,i,j-1,m);
					dp[i][j][m]=getValue2(dp,N,M,i,j+1,m);
					dp[i][j][m]=getValue2(dp,N,M,i,j,m+1);
					dp[i][j][m]=getValue2(dp,N,M,i,j,m-1);
				}
			}
		}
		return dp[a][b][k]*Math.pow(0.25,k);
	}
	public static int getValue2(int[][][] dp,int N,int M,int k,int i,int j){
		if(i<0||i>=N||j<0||j>=M){
			return 0;
		}
		return dp[k][i][j];
	}

	public static int minCoins1(int[] arr,int aim){
		return process1(arr,aim,0,0);
	}
	public static int process1(int[] arr,int aim,int now,int index){
		if(now>aim){
			return -1;
		}
		if(index==arr.length){
			return aim==now?0:-1;
		}
		
		int have=process1(arr,aim,now+arr[index],index+1);
		int no=process1(arr,aim,now,index+1);
		int res=0;
		if(have==-1&&no==-1){
			return -1;
		} else {
			if(have==-1){
				return no;
			} else if(no==-1){
				return have+1;
			} else {
				return Math.min(no,have+1);
			}
		}
	}

	public static int minCoins2(int[] arr,int aim){
		if(arr==null||arr.length==0||aim<0){
			return -1;
		}
		
		int len=arr.length;
		int[][] dp=new int[aim+1][len+1];
		for(int i=0;i<=aim;i++){
			dp[i][len]=-1;
		}
		dp[aim][len]=0;
		for(int j=len-1;j>=0;j--){
			for(int i=0;i<=aim;i++){
				
				if(dp[i][j+1]==-1&&(i+arr[j]>aim||dp[i+arr[j]][j+1]==-1)){
					dp[i][j]=-1;
				} else {
					if(dp[i][j+1]==-1){
						dp[i][j]=dp[i+arr[j]][j+1]+1;
					} else if(i+arr[j]>aim||dp[i+arr[j]][j+1]==-1){
						dp[i][j]=dp[i][j+1];
					} else{
						dp[i][j]=Math.min(dp[i][j+1],dp[i+arr[j]][j+1]+1);
					}
				}
			}
		}
		return dp[0][0];
	}

	//这是有错误的，以 [3,5,10,2]、10为例子：
	//   3 3 2 2
	//   2 2 3 3
	//   3 5 2
	//   3 2 5
	//   5 3 2
	//   5 2 3
	//   2 3 5
	//   2 5 3
	//   .......
	//   有很多重复的
	//   所以我自己的写法是错误的
	public static int infiniteCoins1(int[] arr,int rest){
		if(rest<0){
			return 0;
		}
		if(rest==0){
			return 1;
		}
		int size=arr.length;
		int count=0;
		for(int i=0;i<size;i++){
			count+=infiniteCoins1(arr,rest-arr[i]);
		}
		return count;
	}

	//错误的
	public static int infiniteCoins2(int[] arr,int aim){
		if(arr==null||arr.length<1||aim<0){
			return -1;
		}
		
		int[] dp=new int[aim+1];
		dp[0]=1;
		int len=arr.length;
		int count=0;
		int index=0;
		for(int i=1;i<=aim;i++){
			count=0;
			for(int j=0;j<len;j++){
				index=i-arr[j];
				if(index>=0){
					count+=dp[index];
				}
			}
			dp[i]=count;
		}
		return dp[aim];
	}
 
	//左程云实现
	//arr里都是正数，没有重复值，每一个值代表一种货币，每一种都可以使用无限张
	//最终要找的零钱数是aim
	//找零方法数返回
	public static int ways1(int[] arr,int aim){
		return process(arr,0,aim);
	}
	
	//可以自由使用arr[index...]所有的面值
	//需要搞定的钱数是rest
	//返回找零的方法数
	public static int process(int[] arr,int index,int rest){
		if(index==arr.length){
			return rest==0?1:0;
		}

		//arr[index] 0张、1张、、、、、、，不要超过rest的钱数
		int ways=0;
		for(int n=0;n*arr[index]<=rest;n++){//使用0张、1张、2张、、、、
			ways+=process(arr,index+1,rest-arr[index]*n);
		}
		return ways;
	}
	
	//严格表结构
	public static int ways2(int[] arr,int aim){
		if(arr==null||arr.length==0||aim<0){
			return -1;
		}

		int len=arr.length;
		int[][] dp=new int[len+1][aim+1];
		for(int i=0;i<=aim;i++){
			dp[len][i]=0;
		}
		dp[len][0]=1;
		int count=0   ;
		for(int i=len-1;i>=0;i--){
			for(int j=0;j<=aim;j++){
				count=0;
				for(int n=0;n*arr[i]<=j;n++){
					count+=dp[i+1][j-n*arr[i]];
				}
				dp[i][j]=count;
			}
		}
		return dp[0][aim];
	}

	//严格表结构，优化版本
	public static int ways2_1(int[] arr,int aim){
		if(arr==null||arr.length==0||aim<0){
			return 0;
		}
		int len=arr.length;
		int[][] dp=new int[len+1][aim+1];
		dp[len][0]=1;
		for(int index=len-1;index>=0;index--){
			for(int rest=0;rest<=aim;rest++){
				dp[index][rest]=dp[index+1][rest];
				if(rest-arr[index]>=0){
					dp[index][rest]+=dp[index][rest-arr[index]];
				}
			}
		}
		return dp[0][aim];
	}
	
	//左程云实现，未优化版本
	public static int ways3(int[] arr,int aim){
		if(arr==null||arr.length<1||aim<0){
			return 0;
		}
		
		int len=arr.length;
		int[][] dp=new int[len+1][aim+1];
		dp[len][0]=1;
		for(int index=len-1;index>=0;index--){
			for(int rest=0;rest<=aim;rest++){
				int ways=0;
				for(int n=0;n*arr[index]<=rest;n++){
					ways+=dp[index+1][rest-n*arr[index]];
				}
				dp[index][rest]=ways;
			}
		}
		return dp[0][aim];
	}
	
	public static int ways3_1(int[] arr,int aim){
		if(arr==null||arr.length==0||aim<0){
			return 0;
		}

		int N=arr.length;
		int[][] dp=new int[N+1][aim+1];
		dp[N][0]=1;
		for(int index=N-1;index>=0;index--){
			for(int rest=0;rest<=aim;rest++){
				dp[index][rest]=dp[index+1][rest];
				if(rest-arr[index]>=0){
					dp[index][rest]+=dp[index][rest-arr[index]];
				}
			}
		}
		return dp[0][aim];
	}
	//1、确定可变参数的变化范围，2个可变参数就是二维表；3个可变参数就是三维表、、、
	//2、标出最终状态，也就是需要返回的值
	//3、确定base case
	//4、推出依赖关系，确定计算顺序
	

	//KMP算法：字符串匹配问题
	public static int[] getNext1(String str){
		if(str==null||str.length()==0){
			return null;
		}
		char[] arr=str.toCharArray();
		int len=arr.length;
		int[] next=new int[len];
		next[0]=-1;
		if(len>1){
			next[1]=0;
		}
		for(int i=2;i<len;i++){
			int preIndex=next[i-1];
			while(preIndex>=0&&arr[i-1]!=arr[preIndex]){
				preIndex=next[preIndex];
			}
			next[i]=preIndex+1;
		}
		return next;
	}
	public static int KMP1(String str1,String str2){
		if(str1==null||str2==null||str1.length()<1||str2.length()<1){
			return -1;
		}

		int[] next=getNext1(str2);
		int len1=str1.length();
		int len2=str2.length();
		int i=0;
		int j=0;
		while(i<len1&&j<len2){
			if(str1.charAt(i)==str2.charAt(j)){
				i++;
				j++;
			} else{
				j=next[j];
				if(j==-1){
					i++;
					j=0;
				}
			}
		}
		return j==len2?i-len2:-1;
	}

	public static int[] getNext2(String str){
		if(str==null||str.length()<1){
			return null;
		}
		int len=str.length();
		int[] next=new int[len];
		next[0]=-1;
		if(len>1){
			next[1]=0;
		}
		int i=2;
		int cn=0;//代表此时最长公共前后缀是多少
		while(i<len){
			if(str.charAt(i-1)==str.charAt(cn)){
				next[i++]=++cn;
			} else if(cn>0){
				cn=next[cn];
			} else {
				next[i++]=0;
			}
		}
		return next;
	}
	public static int KMP2(String str1,String str2){
		if(str1==null||str2==null||str1.length()==0||str2.length()==0){
			return -1;
		}
	
		int[] next=getNext2(str2);
		int len1=str1.length();
		int len2=str2.length();
		int i=0;
		int j=0;
		while(i<len1&&j<len2){
			if(str1.charAt(i)==str2.charAt(j)){
				i++;
				j++;
			} else if(j>0){
				j=next[j];
			} else{
				i++;
			}
		}
		return j==len2?i-j:-1;
	}

	//Manacher算法：最长回文子串
	public static int manacher1(String s){
		if(s==null||s.length()==0){
			return 0;
		}

		int len=s.length();
		String str="#";
		for(int i=0;i<len;i++){
			str+=(s.charAt(i)+"#");
		}
		
		len=len*2+1;
		int[] manachers=new int[len];//回文半径数组
		manachers[0]=1;
		int r=1;
		int c=0;//[c,r)

		int max=1;
		for(int i=1;i<len;i++){
			if(i>=r){//此时i落在r外，暴力中心扩散
				int right=i+1;
				int left=i-1;
				while(left>=0&&right<len&&str.charAt(left)==str.charAt(right)){
					left--;
					right++;
				}
				if(right>r){
					r=right;
					c=i;
				}
				manachers[i]=right-i;
			} else {//此时 i 落在r内
				int iLeft=2*c-r;//(iLeft,r)
				int sysi=2*c-i;
				int sysiRadius=manachers[sysi];
				int sysiRight=sysi+sysiRadius;
				int sysiLeft=sysi-sysiRadius;//(sysiLeft,sysiRight)
				
				if(sysiLeft>iLeft){//左边界在内部
					manachers[i]=sysiRadius;
				} else if(sysiLeft<iLeft){//左边界外部
					manachers[i]=r-i;
				} else {//左边界重合
					int right=r;
					int left=2*i-r;
					while(left>=0&&right<len&&str.charAt(left)==str.charAt(right)){
						left--;
						right++;
					}
					if(right>r){
						r=right;
						c=i;
					}
					manachers[i]=right-i;
				}
			}
			max=Math.max(max,manachers[i]);
		}
		return max-1;
	}
	
	public static int manacher2(String s){
		if(s==null||s.length()==0){
			return 0;
		}
		String str="#";
		int len=s.length();
		for(int i=0;i<len;i++){
			str+=(s.charAt(i)+"#");
		}
		
		len=len*2+1;
		int[] manachers=new int[len];
		manachers[0]=1;
		int c=0;
		int r=1;
		int max=1;
		for(int i=1;i<len;i++){
			int sysi=2*c-i;
			int radius=i<r?Math.min(manachers[sysi],r-i):1;
			int right=i+radius;
			int left=i-radius;
			while(right<len&&left>=0&&str.charAt(left)==str.charAt(right)){
				left--;
				right++;
			}
			if(r<right){
				r=right;
				c=i;
			}
			manachers[i]=right-i;
			max=Math.max(max,manachers[i]);
		}
		return max-1;
	}
	

	

	public static void main(String[] args){
		
		//测试manacher算法
		System.out.println(manacher1("ACABBCBBABCDEDCBA"));
		System.out.println(manacher2("ACABBCBBABCDEDCBA"));
		System.out.println("\n\n\n");

		//测试KMP算法
		System.out.println(KMP1("abcdefg","dc"));
		System.out.println(KMP1("abbacdefg","cd"));
		System.out.println(KMP1("abcdefg","efg"));
		System.out.println(KMP1("abcdefg","efh"));
		System.out.println(KMP1("ABACDA","ABAB"));
		System.out.println(KMP2("ABACDA","ABAB"));
		System.out.println(KMP1("ABCABAABCABC","CACA"));
		System.out.println(KMP2("AABCACACABAC","CACA"));


		int x=10;
		int y=9;
		int a=4;
		int b=4;
		int k=6;
		System.out.println(horseToPos1(x,y,a,b,1,1,k));
		System.out.println(horseToPos2(x,y,a-1,b-1,0,0,k));
		System.out.println(horseToPos3(x,y,a-1,b-1,0,0,k));
		System.out.println(getWays(a-1,b-1,k));
		System.out.println(dpWays(a-1,b-1,k));	
		System.out.println("\n\n\n");

		int[] arr={2,3,5,7};
		int aim=10;
		System.out.println(minCoins1(arr,aim));
		System.out.println(minCoins2(arr,aim));
		
		int[] arr1={3,5,10,2};
		aim=10;
		System.out.println(infiniteCoins1(arr1,aim));
		System.out.println(infiniteCoins2(arr1,aim));
		System.out.println(ways1(arr1,aim));
		System.out.println(ways2(arr1,aim));
		System.out.println(ways2_1(arr1,aim));
		System.out.println(ways3(arr1,aim));

		System.out.println("hello world");
	}
}
