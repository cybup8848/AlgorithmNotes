/*************************************************************************
    > File Name: question37.java
    > Author:cheng yingbin
    > Mail: 703477993@qq.com 
    > Created Time: Sun Jul  7 23:07:49 2024
 ************************************************************************/

import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class question37{

	public static class Line{
		public int start;
		public int end;

		public Line(int s,int e){
			start=s;
			end=e;
		}
	}
	
	public static class ComparatorLine implements Comparator<Line>{
		@Override
		public int compare(Line o1,Line o2){
			return o1.start-o2.start;
		}
	}

	//返回重合线段
	public static Line getMaxOverlapsLine(Line[] lines){
		if(lines==null||lines.length==0){
			return null;
		}
		
		PriorityQueue<Line> lineSet=new PriorityQueue<>(new ComparatorLine());
		int len=lines.length;
		for(int i=0;i<len;i++){
			lineSet.add(lines[i]);
		}

		PriorityQueue<Integer> endSet=new PriorityQueue<>();

		int count=0;
		int left=-1;
		int right=-1;
		while(!lineSet.isEmpty()){
			Line line=lineSet.poll();
			while(!endSet.isEmpty()&&endSet.peek()<=line.start){
				endSet.poll();
			}

			endSet.add(line.end);
			int size=endSet.size();
			if(count<size){
				count=size;
				left=line.start;
				right=endSet.peek();
			}
		}
		
		return new Line(left,right);
	}

	//返回最多重合数
	public static int getMaxOverlapsCount(Line[] lines){
		if(lines==null||lines.length==0){
			return 0;
		}
		
		PriorityQueue<Line> lineSet=new PriorityQueue<>(new ComparatorLine());
		int len=lines.length;
		for(int i=0;i<len;i++){
			lineSet.add(lines[i]);
		}

		PriorityQueue<Integer> endSet=new PriorityQueue<>();

		int count=0;
		int left=-1;
		int right=-1;
		while(!lineSet.isEmpty()){
			Line line=lineSet.poll();
			while(!endSet.isEmpty()&&endSet.peek()<=line.start){
				endSet.poll();
			}

			endSet.add(line.end);
			int size=endSet.size();
			if(count<size){
				count=size;
				left=line.start;
				right=endSet.peek();
			}
		}
		
		return count;
	}


	public static class Rectangle{
		public int leftx;
		public int lefty;
		public int rightx;
		public int righty;

		public Rectangle(int x1,int y1,int x2,int y2){
			leftx=x1;
			lefty=y1;
			rightx=x2;
			righty=y2;
		}
	}

	public static class ComparatorRectangle1 implements Comparator<Rectangle>{
		@Override
		public int compare(Rectangle rec1,Rectangle rec2){
			return rec1.lefty-rec2.lefty;
		}
	}
	
	public static class ComparatorRectangle2 implements Comparator<Rectangle>{
		@Override
		public int compare(Rectangle rec1,Rectangle rec2){
			return rec1.righty-rec2.righty;
		}
	}
	
	public static int getMaxOverlapsRecCount(Rectangle[] recs){
		if(recs==null||recs.length<2){
			return 0;
		}

		TreeSet<Rectangle> recsSet=new TreeSet<>(new ComparatorRectangle1());
		int len=recs.length;
		for(int i=0;i<len;i++){
			recsSet.add(recs[i]);
		}

		TreeSet<Rectangle> restRecsSet=new TreeSet<>(new ComparatorRectangle2());
		
		int max=0;
		for(Rectangle rec:recsSet){
			while(!restRecsSet.isEmpty()){
				Rectangle firstVal=restRecsSet.first();
				if(firstVal.righty>rec.lefty){
					break;
				}
				restRecsSet.remove(firstVal);
			}
			
			restRecsSet.add(rec);
			Line[] lines=new Line[restRecsSet.size()];
			int i=0;
			for(Rectangle tmp:restRecsSet){
				lines[i++]=new Line(tmp.leftx,tmp.rightx);
			}
			int count=getMaxOverlapsCount(lines);
			max=Math.max(max,count);
		}

		return max;
	}



	public static void main(String[] args){
		Line[] lines={
			new Line(2,5),
			new Line(1,9),
			new Line(3,10),
			new Line(6,8),
			new Line(2,4)
		};

		Line res=getMaxOverlapsLine(lines);
		System.out.println(res.start+"	"+res.end);
		System.out.println(getMaxOverlapsCount(lines));
		
		System.out.println("\n\n\n");

		Rectangle[] recs={
			new Rectangle(0,0,4,2),
			new Rectangle(3,1,7,3),
			new Rectangle(5,4,9,7),
			new Rectangle(7,6,8,8),
			new Rectangle(6,5,10,9)
		};
		System.out.println(getMaxOverlapsRecCount(recs));



		System.out.println("hello world");
	}
}







