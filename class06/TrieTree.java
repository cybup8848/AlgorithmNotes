/*************************************************************************
    > File Name: TrieTree.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Sun Jan 28 20:49:51 2024
 ************************************************************************/
public class TrieTree{
	public static class TrieNode{
		public int pass;//加前缀树的时候，这个值到达/通过多少次
		public int end;// 这个节点是否是一个字符串的结尾节点。如果是的话，他是多少个字符串的结尾节点
		public TrieNode[] nexts;// HashMap<Char,Node> nexts;，下一级路，key代表有某一条路，value：代表通过这条路走到下一级node
		//路和路之间是有序组织的，用TreeMap<Char,Node> nexts，有序表

		public TrieNode(){
			pass=0;
			end=0;
			// nexts[0]==null 没有走向'a'的路
			// nexts[0] !=null 有走向'a'的路
			// ...
			// nexts[25] !=null 有走向'z'的路
			// 如果字符种类特别多，可以使用HashMap
			nexts=new TrieNode[26];//先建出来有26条路。有没有这条路，是通过下一级有没有节点表达的
		}
	}

	public static class Trie{
		private TrieNode root;

		public Trie(){
			root=new TrieNode();
		}

		public void insert(String word){
			if(word==null){
				return;
			}
			char[] chs=word.toCharArray();
			TrieNode node=root;
			node.pass++;
			int index=0;
			for(int i=0;i<chs.length;i++){//从左到右遍历字符
				index=(int)(chs[i]-'a');//由字符，对应成走哪条路
				if(node.nexts[index]==null){
					node.nexts[index]=new TrieNode();
				}
				node=node.nexts[index];
				node.pass++;
			}
			node.end++;
			//根节点的pass值：表示加入了多少个字符串，也就是有多少个字符串是以空串作为前缀的。任何一个字符串都以空串作为前缀
		}
		
		//word这个单词之前加入过几次
		//自己实现
		public int search1(String word){//查询是否有这个字符串
			if(word==null){
				return 0;//     word==null 与 word=""，不一样
			}
			char[] chs=word.toCharArray();
			int len=chs.length;
			int count=0;
			int index=0;
			TrieNode node=null;
			node=root;
			for(int i=0;i<len;i++){
				index=(int)(chs[i]-'a');
				if(node.nexts[index]==null){
					count=0;
					break;
				}
				node=node.nexts[index];
				count=node.end;
			}
			return count;
		}

		//左程云实现
		//word这个单词之前加入过几次
		public int search(String word){
			if(word==null){
				return 0;
			}
			
			char[] chs=word.toCharArray();
			int len=chs.length;

			TrieNode node=root;
			int index=0;
			for(int i=0;i<len;i++){
				index=(int)(chs[i]-'a');
				if(node.nexts[index]==null){
					return 0;
				}
				node=node.nexts[index];
			}
			return node.end;
		}

		//删除字符串word，自己实现，考虑到可能添加了多个相同的字符串
		//删除所有word
		public void delete1(String word){//删除这个字符串
			int count=search1(word);
			if(count!=0){//确定树中确实加入过word，才删除
				char[] chs=word.toCharArray();
				int len=chs.length;
				int index=0;
				TrieNode node=root;
				node.pass-=count;
				for(int i=0;i<len;i++){
					index=(int)(chs[i]-'a');
					node.nexts[index].pass-=count;
					if(node.nexts[index].pass==0){
						node.nexts[index]=null;
						return;
					}
					node=node.nexts[index];
				}
				node.end=0;
			}
		}
		
		//左程云实现
		//删除一个word
		public void delete(String word){
			if(search(word)!=0){//确定树中确实加入过word，才删除
				char[] chs=word.toCharArray();
				TrieNode node=root;
				node.pass--;
				int index=0;
				int len=chs.length;
				for(int i=0;i<len;i++){
					index=(int)(chs[i]-'a');
					if(--node.nexts[index].pass==0){
						//C++要遍历到底去析构
						node.nexts[index]=null;
						//...
						return;
					}
					node=node.nexts[index];
				}
				node.end--;
			}
		}


		//所有加入的字符串中，有几个是以pre这个字符串作为前缀的
		//自己实现
		public int prefixNumber1(String pre){
			char[] chs=pre.toCharArray();
			int len=chs.length;
			int index=0;
			TrieNode node=root;
			for(int i=0;i<len;i++){
				index=(int)(chs[i]-'a');
				if(node.nexts[index]==null){
					return 0;
				}
				node=node.nexts[index];
			}
			return node.pass;
		}
		
		//左程云实现
		// 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
		public int prefixNumber(String pre){
			if(pre==null){
				return 0;
			}

			char[] chs=pre.toCharArray();
			TrieNode node=root;
			int index=0;
			for(int i=0;i<chs.length;i++){
				index=(int)(chs[i]-'a');
				if(node.nexts[index]==null){
					return 0;
				}
				node=node.nexts[index];
			}
			return node.pass;
		}


	}




	public static void main(String[] args){
		Trie trie=new Trie();
		System.out.println(trie.search("zuo"));
		trie.insert("zuo");
		System.out.println(trie.search1("zuo"));
		trie.insert("asfds");
		trie.insert("abcd");
		System.out.println(trie.prefixNumber1("a"));
		System.out.println(trie.prefixNumber("a"));
		trie.delete1("abcd");
		trie.insert("abcd");
		trie.delete("abcd");
		
		System.out.println("hello world");
	}


}
