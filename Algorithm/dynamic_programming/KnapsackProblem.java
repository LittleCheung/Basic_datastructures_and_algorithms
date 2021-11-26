package com.atguigu.dynamic_programming;

public class KnapsackProblem {

	public static void main(String[] args) {

		int[] w = {1, 4, 3};//物品的重量
		int[] val = {1500, 3000, 2000}; //物品的价值，val[i]就是前面一个的v[i]
		int m = 4; //背包的容量
		int n = val.length; //物品的个数
		

		//创建二维数组，
		//v[i][j]表示在前i个物品中能够装入容量为j的背包中的最大价值
		int[][] v = new int[n+1][m+1];

		//为了记录放入商品的情况，我们定一个二维数组
		int[][] path = new int[n+1][m+1];
		
		//初始化第一行和第一列，表示填入表第一行和第一列都为0
		for(int i = 0; i < v.length; i++) {
			v[i][0] = 0; //将第一列设置为0
		}
		for(int i=0; i < v[0].length; i++) {
			v[0][i] = 0; //将第一行设置0
		}
		
		/*
			动态规划代码：
		 */
		for(int i = 1; i < v.length; i++) { //不处理第一行 i是从1开始的
			for(int j=1; j < v[0].length; j++) {//不处理第一列, j是从1开始的
				if(w[i-1]> j) { // 因为程序i是从1开始的，因此要写成w[i-1]
					v[i][j]=v[i-1][j];
				} else {
					//因为程序i是从1开始的， 因此公式需要调整成：
					//v[i][j]=Math.max(v[i-1][j], val[i-1]+v[i-1][j-w[i-1]]);
					//为了记录商品存放到背包的情况，我们不能直接的使用上面的公式，需要使用if-else来体现公式
					if(v[i - 1][j] < val[i - 1] + v[i - 1][j - w[i - 1]]) {
						v[i][j] = val[i - 1] + v[i - 1][j - w[i - 1]];
						//把当前的情况记录到path
						path[i][j] = 1;
					} else {
						v[i][j] = v[i - 1][j];
					}
					
				}
			}
		}
		
		// 数组v的输出情况
		for(int i =0; i < v.length;i++) {
			for(int j = 0; j < v[i].length;j++) {
				System.out.print(v[i][j] + " ");
			}
			System.out.println();
		}
		
		//输出放入的商品
		int i = path.length - 1; //行的最大下标
		int j = path[0].length - 1;  //列的最大下标
		while(i > 0 && j > 0 ) { //从path的最后开始遍历
			if(path[i][j] == 1) {
				System.out.printf("第%d个商品放入到背包\n", i); 
				j -= w[i-1]; //调整放入w[i-1]后的剩余背包容量
			}
			i--;
		}
		
	}

}
