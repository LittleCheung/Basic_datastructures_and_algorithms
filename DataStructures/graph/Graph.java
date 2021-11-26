package com.atguigu.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Graph {

	// ****************************************************************************************************************
	public static void main(String[] args) {
		/*
			测试图创建的代码：
		 */
		//结点的个数
		int n = 8;
		String Vertexs[] = {"1", "2", "3", "4", "5", "6", "7", "8"};
		//创建图对象
		Graph graph = new Graph(n);
		//循环添加顶点
		for(String vertex: Vertexs) {
			graph.insertVertex(vertex);
		}

		//添加顶点间边的关系
		graph.insertEdge(0, 1, 1);
		graph.insertEdge(0, 2, 1);
		graph.insertEdge(1, 3, 1);
		graph.insertEdge(1, 4, 1);
		graph.insertEdge(3, 7, 1);
		graph.insertEdge(4, 7, 1);
		graph.insertEdge(2, 5, 1);
		graph.insertEdge(2, 6, 1);
		graph.insertEdge(5, 6, 1);

		//显示邻接矩阵
		graph.showGraph();
		
//		//测试DFS遍历的代码：
//		System.out.println("深度遍历");
//		graph.dfs(); // A->B->C->D->E [1->2->4->8->5->3->6->7]
//
//		//测试BFS遍历的代码：
//		System.out.println("广度优先!");
//		graph.bfs(); // A->B->C->D-E [1->2->3->4->5->6->7->8]
	}
	// ***************************************************************************************************************



	private ArrayList<String> vertexList; //存储顶点的集合
	private int[][] edges; //存储图对应的邻接矩阵
	private int numOfEdges; //表示边的数目

	//定义一个数组boolean[], 记录某个结点是否被访问
	private boolean[] isVisited;

	//带参构造器
	public Graph(int n) {
		//初始化矩阵和vertexList
		edges = new int[n][n];
		vertexList = new ArrayList<String>(n);
		numOfEdges = 0;
	}


	//得到第一个邻接结点的下标
	/**
	 * 
	 * @param index
	 * @return 如果邻接结点存在就返回对应的下标，否则返回-1
	 */
	public int getFirstNeighbor(int index) {
		for(int j = 0; j < vertexList.size(); j++) {
			//说明下一个邻接结点存在
			if(edges[index][j] > 0) {
				return j;
			}
		}
		return -1;
	}
	//根据前一个邻接结点的下标来获取下一个邻接结点
	public int getNextNeighbor(int v1, int v2) {
		for(int j = v2 + 1; j < vertexList.size(); j++) {
			if(edges[v1][j] > 0) {
				return j;
			}
		}
		return -1;
	}


	/*
		深度优先遍历算法（DFS）：访问完当前结点后优先访问当前结点的第一个邻接结点
	 */
	/**
	 *
	 * @param isVisited 表示结点是否已经被访问过
	 * @param i 表示要进行遍历的结点
	 */
	// 对某一个结点进行DFS
	private void dfs(boolean[] isVisited, int i) {
		//首先我们访问该结点
		System.out.print(getValueByIndex(i) + "->");
		//将结点设置为已经访问
		isVisited[i] = true;
		//查找结点i的第一个邻接结点w
		int w = getFirstNeighbor(i);
		//说明邻接结点w存在
		while(w != -1) {
			if(!isVisited[w]) {
				dfs(isVisited, w);
			}
			//如果w结点已经被访问过
			w = getNextNeighbor(i, w);
		}
	}
	//对DFS进行一个重载, 遍历我们所有的结点并都进行DFS
	public void dfs() {
		isVisited = new boolean[vertexList.size()];
		//遍历所有的结点并进行dfsDFS
		for(int i = 0; i < getNumOfVertex(); i++) {
			if(!isVisited[i]) {
				dfs(isVisited, i);
			}
		}
	}


	/*
		广度优先遍历算法（BFS）：分层搜索（队列）
	 */
	/**
	 *
	 * @param isVisited 表示结点是否已经被访问过
	 * @param i 表示要进行遍历的结点
	 */
	//对某一个结点进行BFS
	private void bfs(boolean[] isVisited, int i) {
		int u ; // 表示队列的头结点对应下标
		int w ; // 邻接结点的下标
		//新建一个队列，记录结点访问的顺序
		LinkedList queue = new LinkedList();
		//访问结点并输出结点信息
		System.out.print(getValueByIndex(i) + "=>");
		//将结点标记为已访问
		isVisited[i] = true;
		//将结点加入队列
		queue.addLast(i);
		
		while( !queue.isEmpty()) {
			//取出队列的头结点下标
			u = (Integer)queue.removeFirst();
			//得到第一个邻接结点的下标 w 
			w = getFirstNeighbor(u);
			while(w != -1) {
				//判断是否访问过
				if(!isVisited[w]) {
					System.out.print(getValueByIndex(w) + "=>");
					//标记已经访问
					isVisited[w] = true;
					//加入队列
					queue.addLast(w);
				}
				//以u为结点，继续找w后面的下一个邻接结点
				w = getNextNeighbor(u, w); //体现广度优先特点代码
			}
		}
	}
	//对BFS进行一个重载，遍历所有的结点，都进行广度优先搜索
	public void bfs() {
		isVisited = new boolean[vertexList.size()];
		for(int i = 0; i < getNumOfVertex(); i++) {
			if(!isVisited[i]) {
				bfs(isVisited, i);
			}
		}
	}




	/*
		图中常用的方法
	 */
	//返回结点的个数
	public int getNumOfVertex() {
		return vertexList.size();
	}
	//显示图对应的矩阵
	public void showGraph() {
		for(int[] link : edges) {
			System.err.println(Arrays.toString(link));
		}
	}
	//得到边的数目
	public int getNumOfEdges() {
		return numOfEdges;
	}
	//返回结点i(下标)对应的数据，如：0->"A" 1->"B" 2->"C"
	public String getValueByIndex(int i) {
		return vertexList.get(i);
	}
	//返回v1和v2之间边的权值
	public int getWeight(int v1, int v2) {
		return edges[v1][v2];
	}
	//插入结点
	public void insertVertex(String vertex) {
		vertexList.add(vertex);
	}
	//添加边
	/**
	 * 
	 * @param v1 表示点的下标既是第几个顶点  "A"-"B" ，"A"->0 "B"->1
	 * @param v2 边另一个顶点对应的下标
	 * @param weight 表示两个顶点有关联边
	 */
	public void insertEdge(int v1, int v2, int weight) {
		edges[v1][v2] = weight;
		edges[v2][v1] = weight;
		numOfEdges++;
	}
}
