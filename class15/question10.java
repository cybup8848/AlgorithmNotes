/*************************************************************************
    > File Name: question10.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed May  8 16:17:47 2024
 ************************************************************************/
public class question10{

	//递归
	public static int transformNumsToStr(int[] arr){
		return process(arr,0);
	}
	
	public static int transformNumsToStr(char[] str){
		int len=str.length;
		int[] res=new int[len];
		for(int i=0;i<len;i++){
			res[i]=str[i]-'0';
		}
		return process(res,0);
	}

	public static int transformNumsToStr(String s){
		return transformNumsToStr(s.toCharArray());
	}

	private static int process(int[] arr,int index){
		if(index>=arr.length){
			return 1;
		}
		
		int one=0;
		if(arr[index]!=0){
			one=process(arr,index+1);
		}

		int two=0;
		if(index+1<arr.length){
			int tmp=arr[index]*10+arr[index+1];
			if(tmp>0&&tmp<27){
				two=process(arr,index+2);
			}
		}
		
		return one+two;
	}

	//动态规划
	public static int dpTransformNums2Str(int[] arr){
		if(arr==null){
			return 0;
		}
		
		int len=arr.length;
		int[] res=new int[len];
		if(arr[0]!=0){
			res[0]=1;
		}
		
		if(len>1){
			int tmp=arr[0]*10+arr[1];
			if(tmp>0&&tmp<27){
				res[1]=1;
			}
			res[1]+=res[0];
		}
	

		int tmp=0;
		for(int i=2;i<len;i++){
			if(arr[i]!=0){
				res[i]+=res[i-1];
			}
			
			tmp=arr[i-1]*10+arr[i];
			if(tmp>0&&tmp<27){
				res[i]+=res[i-2];
			}
		}

		return res[len-1];
	}
	
	//左程云实现
	public static int transformNums2Str1(int s){
		if(s<=0){
			return 0;
		}
		return process1(String.valueOf(s).toCharArray(),0);
	}

	//0...index-1 已经转换完毕，并且转换正确
	//str[index...] 能转出多少种有效的字符串表达
	public static int process1(char[] str,int index){
		if(index==str.length){
			return 1;
		}

		//index及其后续是还有数字字符的
		//0...
		if(str[index]=='0'){//开头为0
			return 0;
		}

		//index及其后续是还有数字字符的，并且不以0开头，以1~9开头
		int res=process1(str,index+1);//做了一个决定，就让str[index]自己作为一个部分
		if(index==str.length-1){//除了index之外，后续没有字符串了
			return res;
		}

		//index+1依然没越界
		//index和index+1共同构成一个部分，<27
		if((str[index]-'0')*10+str[index+1]-'0'<27){
			res+=process1(str,index+2);
		}

		return res;
	}

	//左程云实现，动态规划
	public static int dpWays(int num){
		if(num<1){
			return 0;
		}

		char[] str=String.valueOf(num).toCharArray();
		int N=str.length;
		int[] dp=new int[N+1];
		dp[N]=1;
		dp[N-1]=str[N-1]=='0'?0:1;
		for(int i=N-2;i>=0;--i){
			if(str[i]=='0'){
				dp[i]=0;
			} else {
				dp[i]=dp[i+1]+((str[i]-'0')*10+str[i+1]-'0'<27?dp[i+2]:0);
			}
		}
		return dp[0];
	}




	public static void main(String[] args){
		int[] arr={
			1,2,2,5,8
		};
		System.out.println(transformNumsToStr(arr));
		System.out.println(dpTransformNums2Str(arr));
		
		System.out.println(transformNums2Str1(12258));
		System.out.println(dpWays(12258));
		System.out.println("hello world");
	}
}



