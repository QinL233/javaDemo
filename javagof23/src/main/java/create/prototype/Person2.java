package create.prototype;

import java.util.Date;

public class Person2 implements Cloneable{

	private Date time;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	//深克隆
	protected Object clone() throws CloneNotSupportedException {
		Person2 obj=(Person2) super.clone();
		//克隆属性
		obj.time=(Date) this.time.clone();
		return obj;
	}
	
}