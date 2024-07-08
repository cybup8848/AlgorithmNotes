/*************************************************************************
    > File Name: question36add.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri May 17 15:10:24 2024
 ************************************************************************/

import java.util.HashMap;


public class question36add{

	public static class Node{
		public int count;

		public HashMap<String,Node> nexts;

		public Node(){
			count=0;
			
			nexts=new HashMap<>();
		}
	}

	public static class PrefixTree{
		private Node root;

		public PrefixTree(){
			root=new Node();
		}
		
		public boolean add(String s){
			String[] arr=s.split("\\\\");
			
			++root.count;
			Node tmp=root;
			for(String str:arr){
				if(tmp.nexts.containsKey(str)==false){
					tmp.nexts.put(str,new Node());
				}
				tmp=tmp.nexts.get(str);
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
			Node tmp=root;
			Node pre=null;
			for(String str:arr){
				pre=tmp;
				tmp=tmp.nexts.get(str);
				--tmp.count;
				if(tmp.count==0){
					pre.nexts.remove(str);
					return true;
				}
			}
			return true;
		}

		public boolean isExist(String s){
			String[] arr=s.split("\\\\");
			
			Node tmp=root;
			for(String str:arr){
				if(tmp.nexts.containsKey(str)==false){
					return false;
				}
				tmp=tmp.nexts.get(str);
			}
			return true;
		}

		public int count(String s){
			String[] arr=s.split("\\\\");
			
			Node tmp=root;
			for(String str:arr){
				if(tmp.nexts.containsKey(str)==false){
					return 0;
				}
				tmp=tmp.nexts.get(arr);
			}
			return tmp.count;	
		}

		public int size(){
			return root.count;
		}

		public void print(boolean isDepth){
			if(isDepth){//有缩进，按照深度打印
				dfs(root,0);
			} else {
				dfs(root);//直接深度递归
			}
		}


		private void dfs(Node r,int depth){
			if(r==null){
				return;
			}
			
			HashMap<String,Node> nexts=r.nexts;
			for(String key:nexts.keySet()){
				for(int i=0;i<depth;i++){
					System.out.print(' ');
				}
				System.out.println(key);
				dfs(nexts.get(key),depth+1);
			}
		}

		private void dfs(Node r){
			if(r==null){
				return;
			}
			
			HashMap<String,Node> nexts=r.nexts;
			for(String key:nexts.keySet()){
				System.out.print(key+" ");
				dfs(nexts.get(key));
			}
		}
	}
	
	public static void print(String[] directorys){
		if(directorys==null){
			return;
		}

		PrefixTree prefixTree=new PrefixTree();
		for(String dir:directorys)	{
			prefixTree.add(dir);
		}		
		
		prefixTree.print(true);
	}




	public static void main(String[] args){
		String[] dirs={
			"b\\cst","d\\","a\\d\\e","a\\b\\c"
		};

		print(dirs);
		System.out.println("\n\n\n");
	
		PrefixTree prefix=new PrefixTree();
		for(String s:dirs){
			prefix.add(s);
			prefix.print(false);
			System.out.println("\n");
		}
		System.out.println("\n\n\n");

		for(String s:dirs){
			prefix.delete(s);
			prefix.print(false);
			System.out.println("\n");
		}



		System.out.println("hello world");
	}
}



