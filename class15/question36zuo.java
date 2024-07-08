/*************************************************************************
    > File Name: question36zuo.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri May 17 16:49:42 2024
 ************************************************************************/

import java.util.TreeMap;



public class question36zuo{

	public static class Node{
		public String name;

		public TreeMap<String,Node> nextMap;

		public Node(String name){
			this.name=name;
			nextMap=new TreeMap<>();
		}

	}

	public static void print(String[] folderPaths){
		if(folderPaths==null||folderPaths.length==0){
			return;
		}

		//根据字符串，把前缀树建立好，头结点为head
		Node head=generateFolderTree(folderPaths);
		
		//打印
		printProcess(head,0);
	}

	
	public static Node generateFolderTree(String[] folderPaths){
		Node head=new Node("");	//系统根目录，前缀树头结点
		for(String foldPath:folderPaths){
			String[] paths=foldPath.split("\\\\");//java特性，用一个'\'做分割的意思
			Node cur=head;
			for(int i=0;i<paths.length;i++){
				if(!cur.nextMap.containsKey(paths[i])){
					cur.nextMap.put(paths[i],new Node(paths[i]));
				}
				cur=cur.nextMap.get(paths[i]);
			}

		}

		return head;
	}

	//head节点，当前在level层
	public static void printProcess(Node node,int level){
		if(level!=0){
			System.out.println(get2nspace(level)+node.name);
		}
		
		for(Node next:node.nextMap.values()){
			printProcess(next,level+1);
		}
	}

	public static String get2nspace(int n){
		String res="";
		
		for(int i=1;i<n;i++){
			res+=" ";
		}
		return res;
	}
	

	public static void main(String[] args){
		String[] dirs={
			"b\\cst","a\\d\\e","d\\","d\\c"
		};

		print(dirs);



		System.out.println("hello world");
	}
}




