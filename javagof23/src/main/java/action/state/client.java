package action.state;

public class client {

	public static void main(String[] args) {
		/**
		 * 模拟售货机操作
		 * 1.初始状态（可开机）
		 * 2.待机状态（可投币-->记录投币数）
		 * 3.准备状态（选择饮料-->投币数判定）
		 * 4.出货状态（循环）
		 */
		售货机 a=new 售货机();
		System.out.print(1);
		a.投币(4);
		System.out.print(2);
		a.开机();
		System.out.print(3);
		a.投币(4);
		System.out.print(4);
		a.选择("奶茶");
		System.out.print(5);
		a.选择("冰红茶");
		System.out.print(6);
		a.投币(3);
		System.out.print(7);
		a.选择("冰红茶");
		System.out.print(8);
		a.退币();
		System.out.print(9);
		a.投币(5);
		System.out.print(10);
		a.选择("冰红茶");
		System.out.print(11);
		a.退币();
		System.out.print(12);
		a.关机();
		System.out.print(1);
		a.投币(4);
		System.out.print(2);
		a.开机();
		System.out.print(3);
		a.投币(4);
		System.out.print(4);
		a.选择("奶茶");
		System.out.print(5);
		a.选择("冰红茶");
		System.out.print(6);
		a.投币(3);
		System.out.print(7);
		a.选择("冰红茶");
		System.out.print(8);
		a.退币();
		System.out.print(9);
		a.投币(5);
		System.out.print(10);
		a.选择("冰红茶");
		System.out.print(11);
		a.退币();
		System.out.print(12);
		a.关机();
		
	}
}
