package action.chain;

public abstract class Chain {

	//true表示可以处理
	boolean ok = false;
	
	public Chain(boolean ok) {
		this.ok=ok;
	}
	
	//链上下一个责任点
	private Chain next;
	public void setNext(Chain next) {
		this.next=next;
	}
	
	public void action() {
		if(ok) {
			doSomething();
		}else {
			//判断还有下一链
			if(next!=null) {
				System.out.println(getClass().getName()+"：诶呀！搞不定，请下一位大牛吧！");
				next.action();
			}else {
				System.out.println("你的问题我们处理不了");
			}
		}
	}
	
	//具体实现
	abstract protected void doSomething();
}
