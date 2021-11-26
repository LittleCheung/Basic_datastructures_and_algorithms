package com.atguigu.queue;

import java.util.Scanner;

public class ArrayQueueDemo {

	public static void main(String[] args) {
		// 创建选择菜单测试队列
		ArrayQueue queue = new ArrayQueue(3);
		char key = ' ';
		Scanner scanner = new Scanner(System.in);	//接收用户输入
		boolean loop = true;
		//输出选择菜单
		while(loop) {
			System.out.println("s(show): 显示队列");
			System.out.println("e(exit): 退出程序");
			System.out.println("a(add): 添加数据到队列");
			System.out.println("g(get): 从队列取出数据");
			System.out.println("h(head): 查看队列第一个数据");
			key = scanner.next().charAt(0);	//接受一个字符
			switch (key) {
			case 's':
				queue.showQueue();
				break;
			case 'a':
				System.out.println("请输入一个数");
				int value = scanner.nextInt();
				queue.addQueue(value);
				break;
			case 'g':
				try {
					int res = queue.getQueue();
					System.out.printf("取出的数据是%d\n", res);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case 'h':
				try {
					int res = queue.headQueue();
					System.out.printf("队列第一个数据是%d\n", res);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case 'e':
				scanner.close();
				loop = false;
				break;
			default:
				break;
			}
		}
		
		System.out.println("程序退出");
	}

}



/*
创建一个普通队列ArrayQueue类
 */
class ArrayQueue {
	private int maxSize; // 表示数组的最大容量
	private int front; // 队列头部
	private int rear; // 队列尾部
	private int[] arr; // 该数组用于存放数据，模拟队列

	// 创建队列的构造器
	public ArrayQueue(int arrMaxSize) {
		maxSize = arrMaxSize;
		arr = new int[maxSize]; 	// 数组初始化
		front = -1; 	// 指向队列头部，分析出front是指向队列头的前一个位置
		rear = -1; 		// 指向队列尾部，指向队列尾的数据（即就是队列最后一个数据）
	}

	// 判断队列是否满
	public boolean isFull() {
		return rear == maxSize - 1;
	}

	// 判断队列是否为空
	public boolean isEmpty() {
		return rear == front;
	}

	// 添加数据到队列
	public void addQueue(int n) {
		// 判断队列是否满
		if (isFull()) {
			System.out.println("队列满不能加入数据");
			return;
		}
		rear++; // 让rear后移
		arr[rear] = n;
	}

	// 获取队列的数据，出队列
	public int getQueue() {
		// 判断队列是否空
		if (isEmpty()) {
			// 通过抛出异常
			throw new RuntimeException("队列空不能取数据");
		}
		front++; // front后移
		return arr[front];

	}

	// 显示队列的所有数据
	public void showQueue() {
		// 遍历
		if (isEmpty()) {
			System.out.println("队列为空没有数据");
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.printf("arr[%d]=%d\n", i, arr[i]);
		}
	}

	// 显示队列的头数据，注意不是取出数据,只是显示第一个数据为多少
	public int headQueue() {
		// 判断
		if (isEmpty()) {
			throw new RuntimeException("队列为空没有数据");
		}
		return arr[front + 1];
	}
}
