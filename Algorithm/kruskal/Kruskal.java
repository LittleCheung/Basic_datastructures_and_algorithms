package com.atguigu.kruskal;

import java.util.Arrays;

/**
 * 问题描述：需要修路将七个公交站连通，问怎样保证连通且修建总路程最短？
 */
public class Kruskal {

	private int edgeNum; //边的个数
	private char[] vertexs; //顶点数组
	private int[][] matrix; //邻接矩阵
	//使用 INF 表示两个顶点不能连通
	private static final int INF = Integer.MAX_VALUE;


	public static void main(String[] args) {
		char[] vertexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
		int matrix[][] = {
	      /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
	/*A*/ {   0,  12, INF, INF, INF,  16,  14},
	/*B*/ {  12,   0,  10, INF, INF,   7, INF},
	/*C*/ { INF,  10,   0,   3,   5,   6, INF},
	/*D*/ { INF, INF,   3,   0,   4, INF, INF},
	/*E*/ { INF, INF,   5,   4,   0,   2,   8},
	/*F*/ {  16,   7,   6, INF,   2,   0,   9},
	/*G*/ {  14, INF, INF, INF,   8,   9,   0}};
	      
	      //创建Kruskal对象实例
	      Kruskal kruskalCase = new Kruskal(vertexs, matrix);
	      //输出构建的邻接矩阵
	      kruskalCase.print();

		  /*
		  	测试kruskal算法代码：
		   */
	      kruskalCase.kruskal();
	}


	//带参构造器
	public Kruskal(char[] vertexs, int[][] matrix) {
		//初始化顶点数和边的个数
		int vlen = vertexs.length;

		//初始化顶点
		this.vertexs = new char[vlen];
		for(int i = 0; i < vertexs.length; i++) {
			this.vertexs[i] = vertexs[i];
		}
		
		//初始化边
		this.matrix = new int[vlen][vlen];
		for(int i = 0; i < vlen; i++) {
			for(int j= 0; j < vlen; j++) {
				this.matrix[i][j] = matrix[i][j];
			}
		}

		//统计边的条数
		for(int i =0; i < vlen; i++) {
			for(int j = i+1; j < vlen; j++) {
				if(this.matrix[i][j] != INF) {
					edgeNum++;
				}
			}
		}
	}

	//打印邻接矩阵的方法
	public void print() {
		System.out.println("邻接矩阵为: \n");
		for(int i = 0; i < vertexs.length; i++) {
			for(int j=0; j < vertexs.length; j++) {
				System.out.printf("%12d", matrix[i][j]);
			}
			System.out.println();//换行
		}
	}


	/*
		kruskal算法实现代码：
	 */
	public void kruskal() {
		int index = 0; //表示最后结果数组的索引
		int[] ends = new int[edgeNum]; //用于保存"已有最小生成树" 中的每个顶点在最小生成树中的终点
		//创建结果数组, 保存最后的最小生成树
		EData[] rets = new EData[edgeNum];
		
		//获取图中所有的边的集合
		EData[] edges = getEdges();
		
		//按照边的权值大小进行排序(从小到大)
		sortEdges(edges);
		
		//遍历edges数组，将边添加到最小生成树中时，判断准备加入的边是否形成了回路，如果没有就加入结果数组rets中, 否则不能加入
		for(int i=0; i < edgeNum; i++) {
			//获取到第i条边的第1个顶点(起点)
			int p1 = getPosition(edges[i].start);
			//获取到第i条边的第2个顶点（当前边终点）
			int p2 = getPosition(edges[i].end);

			//获取p1这个顶点在已有最小生成树中的终点
			int m = getEnd(ends, p1);
			//获取p2这个顶点在已有最小生成树中的终点
			int n = getEnd(ends, p2);
			//判断是否构成回路（是否有两条边指向同一个终点）
			if(m != n) { //没有构成回路
				ends[m] = n; // 设置m 在"已有最小生成树"中的终点
				rets[index++] = edges[i]; //有一条边加入到rets数组
			}
		}
		//统计并打印 "最小生成树", 输出 结果数组rets
		System.out.println("最小生成树为：");
		for(int i = 0; i < index; i++) {
			System.out.println(rets[i]);
		}
	}
	

	/*
		构建kruskal算法所需要的几个功能函数：
	 */
	/**
	 * 功能：使用kruskal算法前必须先对边进行从小到大排序处理（冒泡排序）
	 * @param edges 边的集合
	 */
	private void sortEdges(EData[] edges) {
		for(int i = 0; i < edges.length - 1; i++) {
			for(int j = 0; j < edges.length - 1 - i; j++) {
				if(edges[j].weight > edges[j+1].weight) {
					EData temp = edges[j];
					edges[j] = edges[j+1];
					edges[j+1] = temp;
				}
			}
 		}
	}
	/**
	 * 功能：传入顶点值返回对应的下标
	 * @param ch 顶点的值，比如'A','B'
	 * @return 返回ch顶点对应的下标，如果找不到，返回-1
	 */
	private int getPosition(char ch) {
		for(int i = 0; i < vertexs.length; i++) {
			if(vertexs[i] == ch) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * 功能: 获取图中的边，放到EData[]数组中，后面我们需要遍历该数组
	 * 是通过matrix 邻接矩阵来获取
	 * EData[] 形式 [['A','B', 12], ['B','F',7], .....]
	 * @return 返回装有全部边的数组EData[]
	 */
	private EData[] getEdges() {
		int index = 0;
		EData[] edges = new EData[edgeNum];
		for(int i = 0; i < vertexs.length; i++) {
			for(int j=i+1; j <vertexs.length; j++) {
				if(matrix[i][j] != INF) {
					edges[index++] = new EData(vertexs[i], vertexs[j], matrix[i][j]);
				}
			}
		}
		return edges;
	}
	/**
	 * 功能: 获取下标为i的顶点的终点(), 用于后面判断两个顶点的终点是否相同
	 * @param ends ： 数组就是记录了各个顶点对应的终点是哪个,ends数组是在遍历过程中逐步形成
	 * @param i : 表示传入的顶点对应的下标
	 * @return 返回的就是 下标为i的这个顶点对应的终点的下标
	 */
	private int getEnd(int[] ends, int i) {
		while(ends[i] != 0) {
			i = ends[i];
		}
		return i;
	}

}




//创建一个类EData，它的对象实例就表示一条边
class EData {

	char start; //边的一个点
	char end; //边的另外一个点
	int weight; //边的权值

	public EData(char start, char end, int weight) {
		this.start = start;
		this.end = end;
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "EData [<" + start + ", " + end + ">= " + weight + "]";
	}
}
