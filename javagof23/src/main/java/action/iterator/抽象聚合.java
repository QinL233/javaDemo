package action.iterator;


public interface 抽象聚合 {
	public void add(Object obj);
	public void remove(Object obj);
	public 抽象迭代器 get();
}
