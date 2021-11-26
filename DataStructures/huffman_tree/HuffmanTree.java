package com.atguigu.huffman_tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HuffmanTree {

	public static void main(String[] args) {
		int arr[] = { 13, 7, 8, 3, 29, 6, 1 };
		Node root = createHuffmanTree(arr);


		//测试赫夫曼树的代码：
		preOrderTest(root); //

		
	}


	//前序遍历的测试方法：
	public static void preOrderTest(Node root) {
		if(root != null) {
			root.preOrder();
		}else{
			System.out.println("是空树不能遍历");
		}
	}

	// 创建赫夫曼树的方法
	/**
	 * 
	 * @param arr 需要创建成哈夫曼树的数组
	 * @return 创建好后的赫夫曼树的根结点
	 */
	public static Node createHuffmanTree(int[] arr) {

		// 1. 遍历 arr 数组
		// 2. 将arr的每个元素构成成一个Node
		// 3. 将Node 放入到ArrayList中
		List<Node> nodes = new ArrayList<Node>();
		// for each循环
		for (int value : arr) {
			nodes.add(new Node(value));
		}
		
		//构建赫夫曼树根结点处理的过程是一个循环的过程
		while(nodes.size() > 1) {
			//从小到大进行
			Collections.sort(nodes);

			//(1) 取出权值最小的结点（二叉树）作为左子结点
			Node leftNode = nodes.get(0);
			//(2) 取出权值第二小的结点（二叉树）作为右子结点
			Node rightNode = nodes.get(1);
			//(3)构建成一棵新的二叉树作为父结点，并挂上左右子结点
			Node parent = new Node(leftNode.value + rightNode.value);
			parent.left = leftNode;
			parent.right = rightNode;

			//(4)从ArrayList删除已经处理过的二叉树
			nodes.remove(leftNode);
			nodes.remove(rightNode);
			//(5)将parent加入到nodes
			nodes.add(parent);
		}
		//返回哈夫曼树的根结点
		return nodes.get(0);
	}
}

// 创建结点类
// 为了让Node对象支持Collections集合排序，让Node 实现Comparable接口

class Node implements Comparable<Node> {
	int value; // 结点权值
	char c; //字符
	Node left; // 指向左子结点
	Node right; // 指向右子结点

	public Node(int value) {
		this.value = value;
	}

	//前序遍历的方法
	public void preOrder() {
		System.out.println(this);
		if(this.left != null) {
			this.left.preOrder();
		}
		if(this.right != null) {
			this.right.preOrder();
		}
	}

	@Override
	public String toString() {
		return "Node [value=" + value + "]";
	}

	@Override
	public int compareTo(Node o) {
		// 对权值进行比较，表示从小到大排序
		return this.value - o.value;
	}

}
