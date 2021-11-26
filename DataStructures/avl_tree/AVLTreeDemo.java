package com.atguigu.avl_tree;



public class AVLTreeDemo {

	public static void main(String[] args) {

		int[] arr = { 10, 11, 7, 6, 8, 9 };  
		//创建一个 AVLTree对象
		AVLTree avlTree = new AVLTree();
		//添加结点
		for(int i=0; i < arr.length; i++) {
			avlTree.add(new Node(arr[i]));
		}
		
		//测试中序遍历操作代码
		System.out.println("中序遍历");
		avlTree.infixOrderTest();

		System.out.println("树的高度=" + avlTree.getRoot().height()); //3
		System.out.println("树的左子树高度=" + avlTree.getRoot().leftHeight()); // 2
		System.out.println("树的右子树高度=" + avlTree.getRoot().rightHeight()); // 2
		System.out.println("当前的根结点=" + avlTree.getRoot());//8

	}
}


/*
	AVL平衡二叉树实现代码：
 */
class AVLTree {
	private Node root;

	public Node getRoot() {
		return root;
	}


	// 添加结点的方法
	public void add(Node node) {
		if (root == null) {
			root = node;// 如果root为空则直接让root指向node
		} else {
			root.add(node);
		}
	}


	// 中序遍历测试方法
	public void infixOrderTest() {
		if (root != null) {
			root.infixOrder();
		} else {
			System.out.println("二叉排序树为空，不能遍历");
		}
	}
}



// 创建Node结点
class Node {
	int value;
	Node left;
	Node right;

	public Node(int value) {
		this.value = value;
	}

	// 返回左子树的高度
	public int leftHeight() {
		if (left == null) {
			return 0;
		}
		return left.height();
	}
	// 返回右子树的高度
	public int rightHeight() {
		if (right == null) {
			return 0;
		}
		return right.height();
	}
	// 返回以该结点为根结点的树的高度
	public int height() {
		return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
	}



	//左旋转方法
	private void leftRotate() {
		//创建新的结点，以当前根结点的值
		Node newNode = new Node(value);
		//把新的结点的左子树（左子结点）设置成当前结点的左子树
		newNode.left = left;
		//把新的结点的右子树设置成当前结点的右子树的左子树
		newNode.right = right.left;
		//把当前结点的值替换成右子结点的值
		value = right.value;
		//把当前结点的右子树设置成当前结点右子树的右子树
		right = right.right;
		//把当前结点的左子结点设置成新的结点
		left = newNode;
	}
	//右旋转方法
	private void rightRotate() {
		Node newNode = new Node(value);
		newNode.right = right;
		newNode.left = left.right;
		value = left.value;
		left = left.left;
		right = newNode;
	}


	@Override
	public String toString() {
		return "Node [value=" + value + "]";
	}


	// 添加结点的方法
	// 递归的形式添加结点，注意需要满足二叉排序树的要求
	public void add(Node node) {
		if (node == null) {
			return;
		}

		// 判断传入的结点的值，和当前子树的根结点的值关系
		if (node.value < this.value) {
			// 如果当前结点左子结点为null
			if (this.left == null) {
				this.left = node;
			} else {
				// 递归的向左子树添加
				this.left.add(node);
			}
		} else { // 添加的结点的值大于 当前结点的值
			if (this.right == null) {
				this.right = node;
			} else {
				// 递归的向右子树添加
				this.right.add(node);
			}
		}

		/*
			考虑双旋转实现AVL树的实现代码：
		 */
		//当添加完一个结点后，如果: (右子树的高度-左子树的高度) > 1 , 左旋转
		if(rightHeight() - leftHeight() > 1) {
			//如果它的右子树的左子树的高度大于它的右子树的右子树的高度
			if(right != null && right.leftHeight() > right.rightHeight()) {
				//先对右子结点进行右旋转
				right.rightRotate();
				//然后在对当前结点进行左旋转
				leftRotate(); //左旋转..
			} else {
				//直接进行左旋转即可
				leftRotate();
			}
			return ; //必须要!!!
		}
		//当添加完一个结点后，如果 (左子树的高度 - 右子树的高度) > 1, 右旋转
		if(leftHeight() - rightHeight() > 1) {
			//如果它的左子树的右子树高度大于它的左子树的高度
			if(left != null && left.rightHeight() > left.leftHeight()) {
				//先对当前结点的左结点(左子树)->左旋转
				left.leftRotate();
				//再对当前结点进行右旋转
				rightRotate();
			} else {
				//直接进行右旋转即可
				rightRotate();
			}
		}
	}


	// 中序遍历
	public void infixOrder() {
		if (this.left != null) {
			this.left.infixOrder();
		}
		System.out.println(this);
		if (this.right != null) {
			this.right.infixOrder();
		}
	}

}
