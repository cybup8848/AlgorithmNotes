/*************************************************************************
    > File Name: question27.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Tue Jul  2 10:22:50 2024
 ************************************************************************/



public class question27{


	public static int maxSubArrXorSum1(int[] arr){
		if(arr==null||arr.length==0){
			return Integer.MIN_VALUE;
		}
		
		int len=arr.length;
		int res=Integer.MIN_VALUE;
		
		for(int i=0;i<len;i++){
			int tmp=0;
			for(int j=i;j<len;j++){
				tmp^=arr[j];
				res=Math.max(tmp,res);
			}
		}
		
		return res;
	}
	

	public static int maxSubArrXorSum2(int[] arr){
		if(arr==null||arr.length==0){
			return Integer.MIN_VALUE;
		}
		
		return process2(arr);
	}

	public static int process2(int[] arr){
		int len=arr.length;
		int[] xorSum=new int[len];
		xorSum[0]=arr[0];
		int res=arr[0];

		for(int i=1;i<len;i++){
			xorSum[i]=arr[i]^xorSum[i-1];
			res=Math.max(res,xorSum[i]);
			for(int j=0;j<i;j++){
				res=Math.max(res,xorSum[i]^xorSum[j]);
			}
		}
		
		return res;
	}


	//左程云实现
	//前缀树构建贪心
	public static int maxSubArrXorSum3(int[] arr){
		if(arr==null||arr.length==0){
			return Integer.MIN_VALUE;
		}
		
		return process3(arr);
	}

	public static int process3(int[] arr){
		int len=arr.length;
		
			
		
		return 0;
	}

	//暴力解法
	//返回arr中的子数组最大异或和，时间复杂度O(N^3)
	public static int maxEOR1(int[] arr){
		if(arr==null||arr.length==0){
			return 0;	
		}
		
		int len=arr.length;

		//尝试必须以arr[i]结尾的子数组，最大异或和是多少，尝试所有的arr[i]
		int ans=Integer.MIN_VALUE;
		for(int i=0;i<len;i++){
			//必须以arr[i]结尾
			//0...i 每一个开头
			for(int start=0;start<=i;start++){
				//arr[start...i]这个子数组
				int sum=0;
				for(int index=start;index<=i;index++){
					sum^=arr[index];
				}
				ans=Math.max(ans,sum);
			}
		}
		
		return ans;
	}
	
	//时间复杂度：O(N^2)
	public static int maxEOR2(int[] arr){
		if(arr==null||arr.length==0){
			return Integer.MIN_VALUE;
		}

		int len=arr.length;
		
		//preSum[i]=arr[0...i]的异或和
		int[] preSum=new int[len];
		preSum[0]=arr[0];
		for(int i=1;i<len;i++){
			preSum[i]=arr[i]^preSum[i-1];
		}
		
		//尝试必须以arr[i]结尾的子数组，最大异或和是多少，尝试所有的arr[i]
		int ans=Integer.MIN_VALUE;
		int sum=0;
		for(int i=0;i<len;i++){
			for(int start=0;start<=i;start++){
				sum=preSum[i]^(start-1==-1?0:preSum[start-1]);
				
				//求出arr[start....i] 这个子数组的异或和
				//arr[start...i]异或和 = arr[0...i]异或和 ^arr[0...start-1]
				ans=Math.max(ans,sum);
			}
		}

		return ans;
	}
	
	//前缀树节点
	public  static class Node{
		int cn;
		Node[] next;
		public Node(){
			cn=0;
			next=new Node[2];
			next[0]=next[1]=null;
		}
	}

	//前缀树
	public static class PreTree{
		public PreTree(){//根据我们情景，我们首先有一个0
			root=new Node();
		}

		//增加
		public boolean insertNode(int num){
			String s=num2str(num);
			char[] str=s.toCharArray();
			int len=str.length;

			Node cur=root;
			cur.cn+=1;
			for(int i=0;i<len;i++){
				if(cur.next[str[i]-'0']==null){
					cur.next[str[i]-'0']=new Node();
				}
				cur=cur.next[str[i]-'0'];
				cur.cn+=1;
			}

			return true;
		}

		//删除
		public boolean deleteNode(int num){
			if(!findNode(num)){
				return false;
			}

			root.cn-=1;
			if(root.cn==0){//如果仅仅剩下num这一个数
				root.next[0]=root.next[1]=null;
				return true;
			}

			String s=num2str(num);
			char[] str=s.toCharArray();
			int len=str.length;
			Node cur=root;
			Node pre=null;
			for(int i=0;i<len;i++){
				pre=cur;
				cur=cur.next[str[i]-'0'];
				cur.cn-=1;
				if(cur.cn==0){
					pre.next[str[i]-'0']=null;
					break;
				}
			}

			return true;
		}

		//修改：相当于先删除oldNum，再增加newNum
		public boolean changeNode(int oldNum,int newNum){
			deleteNode(oldNum);
			insertNode(newNum);
			return true;
		}
		
		//查找
		public boolean findNode(int num){
			String s=num2str(num);
			char[] str=s.toCharArray();
			int len=str.length;

			int i=0;
			Node cur=root;
			while(i<len){
				if(cur.next[str[i]-'0']==null){
					break;
				}
				i+=1;
				cur=cur.next[str[i]-'0'];
			}

			return i==len;
		}

		//获取根节点
		public Node getRoot(){
			return root;
		}

		//进行异或
		public int EOR(int num){
			String s=num2str(num);
			char[] str=s.toCharArray();
			int len=str.length;
			
			Node cur=root;
			int index=0;
			if(str[0]=='1'){//1
				index=0;
				str[0]='1';
				if(cur.next[1]!=null){
					str[0]='0';
					index=1;
				}
			} else {//0
				index=1;
				str[0]='1';
				if(cur.next[0]!=null){
					str[0]='0';
					index=0;
				}
			}
			cur=cur.next[index];

			for(int i=1;i<len;i++){
				if(str[i]=='0'){
					index=0;
					if(cur.next[1]!=null){
						str[i]='1';
						index=1;
					}
				} else {//1
					index=1;
					if(cur.next[0]!=null){
						str[i]='1';
						index=0;
					}
				}
				cur=cur.next[index];
			}

			return bin2num(str);
		}

		//将数字转换为字符串
		private String num2str(int num){
			String s=Integer.toBinaryString(num);
			int len=32-s.length();
			for(int i=0;i<len;i++){
				s=String.valueOf(0)+s;
			}
			return s;
		}

		private int bin2num(char[] str){
			int len=str.length;
			int sum=0;
			int base=1;
			for(int i=len-1;i>0;i--){
				if(str[i]=='1'){
					sum+=base;
				}
				base*=2;
			}
			
			if(str[0]=='1'){
				sum-=base;
			}
			
			return sum;
		}
		

		private Node root;
	}

	//利用前缀树
	public static int maxEOR3(int[] arr){
		if(arr==null||arr.length==0){
			return 0;
		}

		int res=Integer.MIN_VALUE;
		int len=arr.length;

		PreTree preTree=new PreTree();
		preTree.insertNode(0);
		
		int sum=0;
		for(int i=0;i<len;i++){
			sum^=arr[i];

			//numTrie装着所有：一个也没有、0~0、0~1、0~2、0~3、...、0~i-1
			res=Math.max(res,preTree.EOR(sum));
			preTree.insertNode(sum);
		}


		return res;
	}

	
	//利用前缀树，左程云实现
	public static class Node1{
		public Node1[] nexts=new Node1[2];
	}

	//把所有前缀异或和，加入到NumTrie，并按照前缀树组织
	public static class NumTrie{
		public Node1 head=new Node1();

		public void add(int num){
			Node1 cur=head;
			for(int move=31;move>=0;move--){
				int path=(num>>move)&1;//move：向右位移多少位
				cur.nexts[path]=cur.nexts[path]==null?new Node1():cur.nexts[path];
				cur=cur.nexts[path];
			}
		}

		//sum最希望遇到的路径，最大的异或结果返回：O(32)
		//面对的是32位有符号整数
		public int maxXor(int sum){
			Node1 cur=head;
			int res=0;//最后的记过 (num^最优选择) 所得到的值
			for(int move=31;move>=0;move--){
				//当前位如果是0，path就是整数0
				//当前位如果是1，path就是整数1
				int path=(sum>>move)&1;//num：第move位置上的状态提取出来
				
				//sum该位的状态，最期待的路
				int best=move==31?path:(path^1);
				//best：最期待的路-->实际走的路
				
				best=cur.nexts[best]!=null?best:(best^1);

				//path num第move位的状态，best是根据path实际走的路
				res|=(path^best)<<move;
				cur=cur.nexts[best];
			}
			
			return res;
		}
	}

	public static int maxXorSubarray(int[] arr){
		if(arr==null||arr.length==0){
			return 0;
		}

		int max=Integer.MIN_VALUE;
		int sum=0;//一个数都没有的时候，异或和为0
		NumTrie numTrie=new NumTrie();
		numTrie.add(0);
		for(int i=0;i<arr.length;i++){
			sum^=arr[i];// eor  -->  0~i 异或和
			max=Math.max(max,numTrie.maxXor(sum));
			numTrie.add(sum);
		}
		
		return max;
	}



	//计算累加和
	public static int maxSubArrAccumuSum(int[] arr){
		if(arr==null||arr.length==0){
			return 0;
		}
		
		int len=arr.length;
		int res=0;
		int sum=0;
		for(int i=0;i<len;i++){
			sum+=arr[i];
			if(sum>=0){
				res=Math.max(res,sum);
			} else {
				sum=0;
			}
		}
		
		return res;
	}

	




	
	public static void main(String[] args){
		int[] arr={
			3
		};
		System.out.println(maxSubArrXorSum1(arr));
		System.out.println(maxSubArrXorSum2(arr));
		System.out.println(maxEOR1(arr));
		System.out.println(maxEOR2(arr));
		System.out.println(maxEOR3(arr));
		System.out.println(maxXorSubarray(arr));
		System.out.println(maxSubArrAccumuSum(arr));
		System.out.println("\n\n");

		arr=new int[]{
			3,-28,-29,2
		};
		System.out.println(maxSubArrXorSum1(arr));
		System.out.println(maxSubArrXorSum2(arr));
		System.out.println(maxEOR1(arr));
		System.out.println(maxEOR2(arr));
		System.out.println(maxEOR3(arr));
		System.out.println(maxXorSubarray(arr));
		System.out.println(maxSubArrAccumuSum(arr));
		System.out.println("\n\n");


		System.out.println(-1^10);

		
		System.out.println(Integer.toBinaryString(10));
		System.out.println(Integer.toBinaryString(-10));
		System.out.println(Integer.parseInt("10101",2));
		System.out.println("hello world");
	}

}




