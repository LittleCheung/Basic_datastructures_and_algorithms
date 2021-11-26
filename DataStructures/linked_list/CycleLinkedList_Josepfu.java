package com.atguigu.linked_list;


/*
	约瑟夫环：人们站在一个等待被处决的圈子里。 计数从圆圈中的指定点开始，并沿指定方向围绕圆圈进行。 在跳过指定数量的人之后，处刑下一个人。
	对剩下的人重复该过程，从下一个人开始，朝同一方向跳过相同数量的人，直到只剩下一个人，并被释放（类似的问题称为约瑟夫问题）
	即转换描述：
		设编号为 1，2，… n 的 n 个人围坐一圈，约定编号为 k（1<=k<=n）的人从 1 开始报数，数到
		m 的那个人出列，它的下一位又从 1 开始报数，数到 m 的那个人又出列，依次类推，直到所有人出列为止，由此产生一个出队编号的序列
 */
public class CycleLinkedList_Josepfu {

	public static void main(String[] args) {
		// 测试构建的循环单链表，和遍历是否ok
		CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
		circleSingleLinkedList.addBoy(5);// 加入5个人节点
		circleSingleLinkedList.showBoy();
		
		// 测试出圈操作是否正确的代码
		circleSingleLinkedList.countBoy(1, 2, 5); // 出圈顺序2->4->1->5->3
	}
}

/*
	创建一个环形的单向链表
 */
class CircleSingleLinkedList {
	// 创建一个first节点,当前没有编号
	private Boy first = null;

	// 添加人节点，构建成一个环形链表
	public void addBoy(int nums) {
		// nums 做一个数据校验
		if (nums < 1) {
			System.out.println("nums的值不正确");
			return;
		}
		Boy curBoy = null; // 辅助变量，帮助构建环形链表
		// 使用for循环创建环形链表
		for (int i = 1; i <= nums; i++) {
			// 根据编号，创建人节点
			Boy boy = new Boy(i);
			// 如果是第一个人
			if (i == 1) {
				first = boy;
				first.setNext(first); // 构成环
				curBoy = first; // 让curBoy指向第一个人
			} else {
				curBoy.setNext(boy);//
				boy.setNext(first);//
				curBoy = boy;
			}
		}
	}

	// 遍历当前环形链表
	public void showBoy() {
		// 判断链表是否为空
		if (first == null) {
			System.out.println("链表为空");
			return;
		}
		// 因为first不能动，因此我们仍然使用一个辅助指针完成遍历
		Boy curBoy = first;
		while (true) {
			System.out.printf("遍历到的人的编号是%d \n", curBoy.getNo());
			if (curBoy.getNext() == first) {// 说明已经遍历完毕
				break;
			}
			curBoy = curBoy.getNext(); // curBoy后移
		}
	}

	// 根据用户的输入，计算出人出圈的顺序，形成出队序列
	/**
	 * 
	 * @param startNo
	 *            表示从第k个人开始数数
	 * @param countNum
	 *            表示数m下
	 * @param nums
	 *            表示最初有多少人在圈中
	 */
	public void countBoy(int startNo, int countNum, int nums) {
		// 先对数据进行校验
		if (first == null || startNo < 1 || startNo > nums) {
			System.out.println("参数输入有误， 请重新输入");
			return;
		}
		// 创建辅助指针,帮助完成人出圈的操作
		Boy helper = first;
		// 需求创建一个辅助指针(变量) helper , 事先应该指向环形链表的最后这个节点
		while (true) {
			if (helper.getNext() == first) { // 说明helper指向最后一个节点
				break;
			}
			helper = helper.getNext();
		}
		//在人报数前，先让 first 和  helper 移动 k-1 次
		for(int j = 0; j < startNo - 1; j++) {
			first = first.getNext();
			helper = helper.getNext();
		}
		//当人报数时，让first 和 helper 指针同时移动  m-1 次, 然后出圈
		//这里是一个循环操作，直到圈中只有一个节点
		while(true) {
			if(helper == first) { //说明圈中只有一个节点
				break;
			}
			//让 first 和 helper 指针同时 的移动 countNum - 1
			for(int j = 0; j < countNum - 1; j++) {
				first = first.getNext();
				helper = helper.getNext();
			}
			//这时first指向的节点，就是要出圈的人节点
			System.out.printf("出圈的人编号%d\n", first.getNo());
			//这时将first指向的人节点出圈
			first = first.getNext();
			helper.setNext(first); //
			
		}
		System.out.printf("最后留在圈中的人编号%d \n", first.getNo());
		
	}
}

// 创建一个Boy类，表示一个节点
class Boy {
	private int no;// 编号
	private Boy next; // 指向下一个节点,默认null

	public Boy(int no) {
		this.no = no;
	}

	public int getNo() {
		return no;
	}

	public Boy getNext() {
		return next;
	}

	public void setNext(Boy next) {
		this.next = next;
	}

}
