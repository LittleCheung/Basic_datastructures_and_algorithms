package com.atguigu.binary_sort_tree;

public class BinarySortTreeDemo {

	public static void main(String[] args) {

		int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};
		BinarySortTree binarySortTree = new BinarySortTree();

		//循环添加结点到二叉排序树
		for(int i = 0; i< arr.length; i++) {
			binarySortTree.add(new Node(arr[i]));
		}
		
		//中序遍历二叉排序树
		System.out.println("中序遍历二叉排序树~");
		binarySortTree.infixOrderTest(); // 1, 2, 3, 5, 7, 9, 10, 12
		
		//测试删除叶子结点
//	    binarySortTree.delNode(2);
//	    binarySortTree.delNode(5);
//	    binarySortTree.delNode(9);
//	    binarySortTree.delNode(12);


//		System.out.println("root=" + binarySortTree.getRoot());
//		System.out.println("删除结点后");
//		binarySortTree.infixOrderTest();
	}

}

/*
	创建二叉排序树的代码
 */
class BinarySortTree {

	private Node root;

	public Node getRoot() {
		return root;
	}

	//查找要删除结点的方法
	public Node searchTargetNode(int value) {
		if(root == null) {
			return null;
		} else {
			return root.search(value);
		}
	}
	
	//查找父结点的方法
	public Node searchParentNode(int value) {
		if(root == null) {
			return null;
		} else {
			return root.searchParent(value);
		}
	}


	//编写方法要实现的目的：
	//1. 返回以node 为根结点的二叉排序树的最小结点的值
	//2. 将node 为根结点的二叉排序树的最小结点置空
	/**
	 *
	 * @param node 传入的结点(当做二叉排序树的根结点)
	 * @return 返回以node为根结点的二叉排序树的最小结点的值
	 */
	public int delRightTreeMin(Node node) {
		Node target = node;
		//循环查找左子节点，就会找到最小值
		while(target.left != null) {
			target = target.left;
		}
		//这时 target就指向了最小结点
		//将最小结点位置的值置空
		delNode(target.value);
		//返回用target保存的最小结点的值
		return target.value;
	}
	
	
	//删除结点的方法
	public void delNode(int value) {
		if(root == null) {
			return;
		}else {
			//1.需要先找到要删除的结点targetNode
			Node targetNode = searchTargetNode(value);
			//如果没有找到要删除的结点
			if(targetNode == null) {
				return;
			}
			//如果我们发现当前二叉排序树只有一个结点
			if(root.left == null && root.right == null) {
				root = null;
				return;
			}

			//去找到要删除结点targetNode的父结点
			Node parent = searchParentNode(value);
			/*
			情况1：删除的结点是叶子结点
			 */
			if(targetNode.left == null && targetNode.right == null) {
				//判断targetNode是父结点的左子结点，还是右子结点
				if(parent.left != null && parent.left.value == value) { //是左子结点
					parent.left = null;
				} else if (parent.right != null && parent.right.value == value) {//是右子结点
					parent.right = null;
				}
			}
			/*
			 情况2：删除有两棵子树的节点
			 */
			else if (targetNode.left != null && targetNode.right != null) {
				int minVal = delRightTreeMin(targetNode.right);
				targetNode.value = minVal;
			/*
			情况3：删除只有一棵子树的结点
			 */
			} else {
				//如果要删除的结点有左子结点 
				if(targetNode.left != null) {
					if(parent != null) {
						//如果targetNode 是 parent 的左子结点
						if(parent.left.value == value) {
							parent.left = targetNode.left;
						} else { //如果targetNode 是 parent 的右子结点
							parent.right = targetNode.left;
						} 
					} else {
						root = targetNode.left;
					}
				} else {//如果要删除的结点有右子结点
					if(parent != null) {
						//如果targetNode 是 parent 的左子结点
						if(parent.left.value == value) {
							parent.left = targetNode.right;
						} else { //如果targetNode 是 parent 的右子结点
							parent.right = targetNode.right;
						}
					} else {
						root = targetNode.right;
					}
				}
			}
		}
	}
	
	//添加结点的方法
	public void add(Node node) {
		if(root == null) {
			root = node; //如果root为空则直接让root指向node
		} else {
			root.add(node);
		}
	}

	//中序遍历测试的方法
	public void infixOrderTest() {
		if(root != null) {
			root.infixOrder();
		} else {
			System.out.println("二叉排序树为空，不能遍历");
		}
	}
}


//创建Node结点类，作为binarySortTree类的属性
class Node {

	int value;
	Node left;
	Node right;

	public Node(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Node [value=" + value + "]";
	}

// ********************************************************************************************************************

	/*
		无论是哪种删除的情况，都需要知道要删除的节点和其父节点
	 */

	//查找要删除的结点
	/**
	 * 
	 * @param value 希望删除的结点的值
	 * @return 如果找到返回该结点，否则返回null
	 */
	public Node search(int value) {
		if(value == this.value) { //找到就是该结点
			return this;
		} else if(value < this.value) {//如果查找的值小于当前结点，向左子树递归查找
			//如果左子结点为空
			if(this.left  == null) {
				return null;
			}
			return this.left.search(value);
		} else { //如果查找的值不小于当前结点，向右子树递归查找
			if(this.right == null) {
				return null;
			}
			return this.right.search(value);
		}
	}



	//查找要删除结点的父结点
	/**
	 * 
	 * @param value 要找到的结点的值
	 * @return 返回的是要删除的结点的父结点，如果没有就返回null
	 */
	public Node searchParent(int value) {
		//如果当前结点就是要删除的结点的父结点，就返回
		if((this.left != null && this.left.value == value) || (this.right != null && this.right.value == value)) {
			return this;
		} else {
			//如果查找的值小于当前结点的值, 并且当前结点的左子结点不为空
			if(value < this.value && this.left != null) {
				return this.left.searchParent(value); //向左子树递归查找
			} else if (value >= this.value && this.right != null) {
				return this.right.searchParent(value); //向右子树递归查找
			} else {
				return null; // 没有找到父结点
			}
		}
	}

// ********************************************************************************************************************

	//添加结点的方法
	//以递归的形式添加结点，需要满足二叉排序树的要求：左子结点比该结点小，右子结点比该结点大
	public void add(Node node) {

		if(node == null) {
			return;
		}
		//判断传入的结点的值，比较和当前子树的根结点的值的关系
		if(node.value < this.value) { //添加的结点的值小于当前结点的值
			//如果当前结点左子结点为null
			if(this.left == null) {
				this.left = node;
			} else {
				//递归的向左子树添加
				this.left.add(node);
			}
		} else { //添加的结点的值大于当前结点的值
			if(this.right == null) {
				this.right = node;
			} else {
				//递归的向右子树添加
				this.right.add(node);
			}
		}
	}
	
	//中序遍历方法
	public void infixOrder() {
		if(this.left != null) {
			this.left.infixOrder();
		}
		System.out.println(this);
		if(this.right != null) {
			this.right.infixOrder();
		}
	}
	
}
