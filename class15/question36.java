/*************************************************************************
    > File Name: question36.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri May 17 13:45:26 2024
 ************************************************************************/



public class question36{
	
	public static class Node{
		public int count;	

		public Node[] nexts;
		
		public Node(){
			count=0;
			nexts=new Node[26];
			for(int i=0;i<26;i++){
				nexts[i]=null;
			}
		}
	}

	public static class PrefixTree{
		Node root;
		
		public PrefixTree(){
			root=new Node();
		}

		public boolean add(String s){
			String[] arr=s.split("\\\\");

			++root.count;
			Node tmp=root;
			
			int len=arr.length;
			for(int i=0;i<len;i++){
				if(tmp.nexts[arr[i].charAt(0)-'a']==null){
					tmp.nexts[arr[i].charAt(0)-'a']=new Node();
				}
				tmp=tmp.nexts[arr[i].charAt(0)-'a'];
				++tmp.count;
			}
			return true;
		}

		public boolean delete(String s){
			if(isExist(s)==false){
				return false;
			}

			String[] arr=s.split("\\\\");
			
			--root.count;

			Node pre=null;
			Node tmp=root;
			int len=arr.length;
			for(int i=0;i<len;i++){
				pre=tmp;
				tmp=tmp.nexts[arr[i].charAt(0)-'a'];
				--tmp.count;
				if(tmp.count==0){
					pre.nexts[arr[i].charAt(0)-'a']=null;
					return true;
				}
			}
			
			return true;
		}

		public boolean isExist(String s){
			String[] arr=s.split("\\\\");
			Node tmp=root;
			
			int len=arr.length;
			for(int i=0;i<len;i++){
				if(tmp.nexts[arr[i].charAt(0)-'a']==null){
					return false;
				}
				tmp=tmp.nexts[arr[i].charAt(0)-'a'];
			}
			return true;
		}

		public int count(String pre){//以pre为前缀的个数
			String[] arr=pre.split("\\\\");

			Node tmp=root;
			
			int len=arr.length;
			for(int i=0;i<len;i++){
				if(tmp.nexts[arr[i].charAt(0)-'a']==null){
					return 0;
				}
				tmp=tmp.nexts[arr[i].charAt(0)-'a'];
			}
			return tmp.count;
		}

		public int size(){
			return root.count;
		}

		public Node getRoot(){
			return root;
		}
		
	}


	public static void print(String[] directorys){
		PrefixTree prefixTree=new PrefixTree();
		
		for(String s:directorys){
			prefixTree.add(s);
		}

		dfs(prefixTree.getRoot(),0);
	}

	public static void dfs(Node root,int depth){
		if(root==null){
			return;
		}
		
		Node[] nexts=root.nexts;
		int len=nexts.length;
		for(int i=0;i<len;i++){
			if(nexts[i]!=null){
				for(int j=0;j<depth;j++){
					System.out.print(" ");
				}
				System.out.println((char)(i+'a'));
			}
			dfs(nexts[i],depth+1);
		}
	}


	public static void main(String[] args){
		
		String test="a\\b\\c";
		System.out.println(test);

		System.out.println("\n\n\n");

		String[] arr=test.split("\\\\");//split既有转义，又有正则表达式

		for(String str:arr){
			System.out.println(str);
		}
		
		System.out.println("\n\n\n");

		
		String[] direc={
			"b\\f","d\\","a\\d\\e","a\\b\\c"
		};

		print(direc);


		PrefixTree prefix=new PrefixTree();
		for(String s:direc){
			prefix.add(s);
			dfs(prefix.getRoot(),0);
			System.out.println("\n");
		}
		
		System.out.println("\n\n\n");

		
		for(String s:direc){
			prefix.delete(s);
			dfs(prefix.getRoot(),0);
			System.out.println("\n");
		}



		System.out.println("hello world");
	}
}



