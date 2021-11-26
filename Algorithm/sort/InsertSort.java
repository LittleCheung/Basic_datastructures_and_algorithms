package com.atguigu.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class InsertSort {

	public static void main(String[] args) {

		//int[] arr = {101, 34, 119, 1, -1, 89};
		//System.out.println("排序前");
		//System.out.println(Arrays.toString(arr));

		/*
			测试一下直接插入排序的速度O
		 */
		// 创建要给80000个的随机的数组
		int[] arr = new int[80000];
		for (int i = 0; i < 80000; i++) {
			arr[i] = (int) (Math.random() * 80000); // 生成一个[0, 80000) 数
		}

		System.out.println("插入排序前");
		Date data1 = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1Str = simpleDateFormat.format(data1);
		System.out.println("排序前的时间是=" + date1Str);

		//测试插入排序算法
		insertSort(arr);
		
		Date data2 = new Date();
		String date2Str = simpleDateFormat.format(data2);
		System.out.println("排序后的时间是=" + date2Str);

		//System.out.println("排序后");
		//System.out.println(Arrays.toString(arr));
		
		
		
		
	}

	/*
		直接插入排序算法实现代码
	 */
	public static void insertSort(int[] arr) {

		//使用for循环来把代码简化
		for(int i = 1; i < arr.length; i++) {
			//定义待插入的数
			int insertVal = arr[i];
			int insertIndex = i - 1; // 即arr[1]的前面这个数的下标
	
			// 给insertVal 找到插入的位置
			// 说明
			// 1. insertIndex >= 0 保证在给insertVal 找插入位置，不越界
			// 2. insertVal < arr[insertIndex] 待插入的数，还没有找到插入位置
			// 3. 就需要将 arr[insertIndex] 后移
			while (insertIndex >= 0 && insertVal < arr[insertIndex]) {	// 从小到大排序
				arr[insertIndex + 1] = arr[insertIndex];
				insertIndex--;
			}
			// 当退出while循环时，说明插入的位置找到, insertIndex + 1
			// 判断是否需要赋值
			if(insertIndex + 1 != i) {
				arr[insertIndex + 1] = insertVal;
			}

		}
		
		
		/*

		//直接插入排序逐步推导过程：


		//第1轮
		int insertVal = arr[1];	//定义待插入的数
		int insertIndex = 1 - 1; //即arr[1]的前面这个数的下标
		//给insertVal 找到插入的位置
		//说明
		//1. insertIndex >= 0 保证在给insertVal 找插入位置，不越界
		//2. insertVal < arr[insertIndex] 说明待插入的数还没有找到插入位置
		//3. 就需要将 arr[insertIndex] 后移
		while(insertIndex >= 0 && insertVal < arr[insertIndex] ) {
			arr[insertIndex + 1] = arr[insertIndex];// arr[insertIndex]
			insertIndex--;	// 根据debug此时insertIndex变成了-1
		}
		//当退出while循环时，说明插入的位置找到, insertIndex + 1
		arr[insertIndex + 1] = insertVal;

		
		//第2轮
		insertVal = arr[2];
		insertIndex = 2 - 1;
		while(insertIndex >= 0 && insertVal < arr[insertIndex] ) {
			arr[insertIndex + 1] = arr[insertIndex];// arr[insertIndex]
			insertIndex--;
		}
		arr[insertIndex + 1] = insertVal;

		
		
		//第3轮
		insertVal = arr[3];
		insertIndex = 3 - 1;
		while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
			arr[insertIndex + 1] = arr[insertIndex];// arr[insertIndex]
			insertIndex--;
		}
		arr[insertIndex + 1] = insertVal;
	*/
		
	}

}
