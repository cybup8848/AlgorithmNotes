/*************************************************************************
    > File Name: Manacher.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Wed Feb 28 17:36:09 2024
 ************************************************************************/
public class Manacher{
	
	//荷兰国旗问题
	public static void heLan(int[] nums,int flag){
		if(nums==null||nums.length<2){
			return;
		}
		
		int len=nums.length;
		int small=-1;
		int big=len;
		int i=0;
		while(i<big){
			if(nums[i]<flag){
				small++;
				int tmp=nums[small];
				nums[small]=nums[i];
				nums[i]=tmp;
				i++;
			} else if(nums[i]>flag){
				big--;
				int tmp=nums[big];
				nums[big]=nums[i];
				nums[i]=tmp;
			} else {
				i++;
			}
		}

	}

	public static void printArray(int[] nums){
		if(nums==null){
			return;
		}
		int len=nums.length;
		for(int i=0;i<len;i++){
			System.out.print(nums[i]+" ");
		}
		System.out.println("\n");
	}


	//一般解法：中心扩散法，会错失偶数回文
	public static int loopStr(String str){
		if(str==null){
			return 0;
		}
		int len=str.length();
		if(len<2){
			return len;
		}

		int maxLen=1;
		int tmpLen=0;
		for(int i=0;i<len-1;i++){
			
			//计算str[i]为中间，奇数
			tmpLen=1;
			int left=i-1;
			int right=i+1;
			while(left>=0&&right<len){
				if(str.charAt(left)!=str.charAt(right)){
					break;
				}
				tmpLen+=2;
				left--;
				right++;
			}
			maxLen=maxLen<tmpLen?tmpLen:maxLen;
			
			//偶数
			if(str.charAt(i)==str.charAt(i+1)){
				tmpLen=0;
				left=i;
				right=i+1;
				while(left>=0&&right<len){
					if(str.charAt(left)!=str.charAt(right)){
						break;
					}
					tmpLen+=2;
					left--;
					right++;
				}
				maxLen=maxLen<tmpLen?tmpLen:maxLen;
			}
		}
		return maxLen;
	}
	
	//左程云写法：在字符串中添加特殊字符
	//  122131221
	//  #1#2#2#1#3#1#2#2#1#
	//  以新字符串往两边扩，奇数肯定能，偶数的可以用#往两边扩找到
	//  时间复杂度O(n^2)
	//  下面这种写法要求扩充字符不能是字符串s中的字符
	public static int loopStr1(String s){
		if(s==null){
			return 0;
		}
		if(s.length()<2){
			return s.length();
		}
		
		String newStr="";
		int len=s.length();
		for(int i=0;i<len;i++){
			newStr+="#"+s.charAt(i);
		}
		newStr+="#";
		len=newStr.length();
		int maxlen=1;
		int tmplen=0;
		for(int i=0;i<len;i++){
			tmplen=1;
			if(newStr.charAt(i)=='#'){
				tmplen=0;
			}
			
			int left=i-1;
			int right=i+1;
			while(left>=0&&right<len){
				if(newStr.charAt(left)!=newStr.charAt(right)){
					break;
				}
				if(newStr.charAt(left)!='#'){
					tmplen+=2;
				}
				left--;
				right++;
			}
			maxlen=maxlen<tmplen?tmplen:maxlen;
		}
		return maxlen;
	}
	
	//下面的写法对扩充字符没有要求
	public static int loopStr2(String s){
		if(s==null){
			return 0;
		}
		if(s.length()<2){
			return s.length();
		}

		int len=s.length();
		String newStr="";
		for(int i=0;i<len;i++){
			newStr+="#"+s.charAt(i);
		}
		newStr+="#";
		
		len=len*2+1;
		int matchPairs=0;
		//原来字符串的字符都在奇数位置，扩充字符都在偶数位置
		int left=0;
		int right=0;
		int maxlen=1;
		int tmplen=0;
		for(int i=0;i<len;i++){
			left=i-1;
			right=i+1;
			matchPairs=0;
			while(left>=0&&right<len){
				if(newStr.charAt(left)!=newStr.charAt(right)){
					break;
				}
				matchPairs++;
				left--;
				right++;
			}
			tmplen=i%2+matchPairs/2*2;
			maxlen=maxlen<tmplen?tmplen:maxlen;
		}
		return maxlen;
	}


	//Manacher算法：求取最长回文子串长度	
	//自己实现
	public static int manacher(String s){
		if(s==null){
			return 0;
		}
		
		int len=s.length();
		if(len<2){
			return len; 
		}
		
		//处理字符串，将原字符串变为处理串
		String newStr="";
		for(int i=0;i<len;i++){
			newStr+="#"+s.charAt(i);
		}
		newStr+="#";
		len=newStr.length();

		int[] pArrRadius=new int[len];
		int R=-1;//所能到达的最右回文边界
		int C=-1;//此时所能到达的最右回文边界的扩散中心点
		int max=Integer.MIN_VALUE;
		for(int i=0;i<len;i++){
			//此时的点不在最右回文右边界内，暴力中心扩散
			if(i>R){
				int left=i-1;
				int right=i+1;
				while(left>=0&&right<len){
					if(newStr.charAt(left)!=newStr.charAt(right)){
						break;
					}
					left--;
					right++;
				}
				pArrRadius[i]=right-i;
				R=right-1;
				C=i;
			} else { // i 在最右回文边界内部
				int sysPoint=2*C-i;
				int sysPointRadius=pArrRadius[sysPoint];
				int L=2*C-R;
				if(sysPoint-sysPointRadius+1>L){//i'的回文区域在左边界里面，回文半径与i' 相同
					pArrRadius[i]=sysPointRadius;
				} else if(sysPoint-sysPointRadius+1<L){//i‘ 回文区域有一部分在左边界外面，回文半径就是[i,R]
					pArrRadius[i]=R-i+1;
				} else {// i' 回文区域的左边界与C的左边界正好重叠，i的回文半径从R开始往两边开始扩
					int left=2*i-R-1;
					int right=R+1;
					while(left>=0&&right<len){
						if(newStr.charAt(left)!=newStr.charAt(right)){
							break;
						}
						left--;
						right++;
					}
					if(right-1>R){
						R=right-1;
						C=i;
					}
					pArrRadius[i]=right-i;
				}
			}
			max=Math.max(max,pArrRadius[i]);
		}
		
		return max-1;
	}

	//左程云实现
	public static char[] manacherString(String str){
		char[] charArr=str.toCharArray();
		char[] res=new char[str.length()*2+1];
		int index=0;
		for(int i=0;i!=res.length;i++){
			res[i]=(i&1)==0?'#':charArr[index++];//偶数位置#，奇数位置是实际字符
		}
		return res;
	}

	public static int maxLcpsLength(String s){
		if(s==null||s.length()==0){
			return 0;
		}
		
		char[] str=manacherString(s);// 1221--->#1#2#2#1#
		
		int len=str.length;
		int[] pArr=new int[len];//回文半径数组
		int C=-1;//中心
		int R=-1;//回文右边界的再往右一个位置，最右的有效区是R-1位置
		int max=Integer.MIN_VALUE;//扩出来的最大值
		for(int i=0;i!=str.length;i++){//每一个位置都求回文半径
			//i至少的回文区域，先给pArr[i]，这些区域不用验证
			pArr[i]=R>i?Math.min(pArr[2*C-i],R-i):1;//优雅、巧妙、精炼
			while(i+pArr[i]<str.length&&i-pArr[i]>-1){//然后进行中心扩散，省了很多if-else
				if(str[i+pArr[i]]==str[i-pArr[i]]){
					pArr[i]++;
				} else {
					break;
				}
			}
			if(i+pArr[i]>R){
				R=i+pArr[i];
				C=i;
			}
			max=Math.max(max,pArr[i]);
		}
		return max-1;
	}

	public static void main(String[] args){

		//测试荷兰国旗问题
		int[] nums={1,354,132,5675,10,3,57,45,12,46,12,564,3,78,10,465,13,56,2456,12,3,56512,12};
		int flag=12;
		heLan(nums,flag);	
		printArray(nums);

		String str="122131221";
		System.out.println(loopStr(str));
		System.out.println(loopStr1(str));
		System.out.println(loopStr2(str));
		System.out.println(manacherString(str));
		System.out.println(manacher(str));

		System.out.println("hello world");
	}
}
