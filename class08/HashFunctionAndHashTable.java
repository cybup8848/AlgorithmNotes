/*************************************************************************
    > File Name: HashFunctionAndHashTable.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Fri Feb  2 15:53:58 2024
 ************************************************************************/
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.Stack;
import java.util.Map;
import java.util.Set;
import java.util.List;
public class HashFunctionAndHashTable{
	
	//设计 RandomPool 结构
	public static class RandomPool<K>{
		private HashMap<K,Integer> keyIndexMap;
		private HashMap<Integer,K> indexKeyMap;
		private int size;
		
		public RandomPool(){
			this.keyIndexMap=new HashMap<K,Integer>();
			this.indexKeyMap=new HashMap<Integer,K>();
			this.size=0;
		}

		public void insert(K key){
			if(!this.keyIndexMap.containsKey(key)){
				this.keyIndexMap.put(key,this.size);
				this.indexKeyMap.put(this.size++,key);
			}
		}

		public void delete(K key){
			if(this.keyIndexMap.containsKey(key)){
				int deleteIndex=this.keyIndexMap.get(key);
				int lastIndex=--this.size;
				K lastKey=this.indexKeyMap.get(lastIndex);
				this.keyIndexMap.put(lastKey,deleteIndex);
				this.indexKeyMap.put(deleteIndex,lastKey);
				this.keyIndexMap.remove(key);
				this.indexKeyMap.remove(lastIndex);
			}
		}

		public K getRandom(){
			if(this.size==0){
				return null;
			}
			int randomIndex=(int)(Math.random()*this.size);//0 ~ size-1
			return this.indexKeyMap.get(randomIndex);
		}
	}

	//布隆过滤器
	//自己实现
	public static class BloomFilter{
		int[] arr;
		int size;

		public BloomFilter(int size){
			arr=new int[size];
			this.size=size*8;//多少个比特
		}
		
		public void addNum(int num){
			int hashVal1=num*2;
			int hashVal2=num*5;
			int hashVal3=num*7;

			int numIndex=(hashVal1%size)/32;
			int bitIndex=(hashVal1%size)%32;
			arr[numIndex]=arr[numIndex]|(1<<bitIndex);

			numIndex=(hashVal2%size)/32;
			bitIndex=(hashVal2%size)%32;
			arr[numIndex]=arr[numIndex]|(<<bitIndex);

			numIndex=(hashVal3%size)/32;
			bitIndex=(hashVal3%size)%32;
			arr[numIndex]=arr[numIndex]|(1<<bitIndex);
		}

		public boolean isExist(int num){
			int hashVal1=num*2;
			int hashVal2=num*5;
			int hashVal3=num*7;

			int numIndex=(hashVal1%size)/32;
			int bitIndex=(hashCal1%size)%32;
			int flag1=(arr[numIndex]>>bitIndex)&1;

			numIndex=(hashVal2%size)/32;
			bitIndex=(hashVal2%size)%32;
			int flag2=(arr[numIndex]>>bitIndex)&1;

			numIndex=(hashVal3%size)/32;
			bitIndex=(hashVal3%size)%32;
			int flag3=(arr[numIndex]>>bitIndex)&1;

			return flag1+flag2+flag3==3?true:false;
		}
	}

	//左程云实现
	

	
	public static void main(String[] args){
		RandomPool<String> pool=new RandomPool<String>();
		pool.insert("zuo");
		pool.insert("cheng");
		pool.insert("yun");
		System.out.println(pool.getRandom());


		//布隆过滤器
		int[] arr=new int[10];//32bit * 10 --> 320 bits
		//arr[0]：int 0~31
		//arr[1]：int 32~63
		//arr[2]：int 64~95

		int i=178;//想取得第178个bit的状态，从0开始算起，位数组从0开始算起

		int numIndex=i/32;
		int bitIndex=i%32;

		//拿到178位的状态
		//低位0到高位31，从右开始数
		int s=(arr[numIndex]>>bitIndex)&1;

		//请把178位的状态改为1
		arr[numIndex]=arr[numIndex]|(1<<bitIndex);

		i=178;//请把第178位的状态改成0
		arr[numIndex]=arr[numIndex]&(~(1<<bitIndex));

		i=178;//请把第178位的状态拿出来
		//bit 0 1
		int bit=(arr[i/32]>>(i%32))&1;


		
		
		
		
		
		System.out.println("hello world");
	}
}
