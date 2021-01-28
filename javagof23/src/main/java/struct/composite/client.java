package struct.composite;

public class client {

	public static void main(String[] args) {
		/**
		 * 1.需求：袋子下装袋子（容器）或商品（叶子），最终展示树状结构和总计
		 */
		//总计
		double s=0;
		
		容器 a,b,c;
		叶子 n;
		//创建袋子
		a=new 容器("大箱子");
		b=new 容器("中箱子");
		c=new 容器("小箱子");
		//创建商品
		n=new 叶子("巧克力",1,6.5);
		a.add(n);
		a.add(b);
		n=new 叶子("牛奶",2,8.8);
		b.add(n);
		b.add(c);
		n=new 叶子("咖啡",4,7.3);
		c.add(n);
		//输出
		System.out.println("总额"+a.total());
		a.show();
	}

}
