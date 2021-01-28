package create.prototype;

import java.io.Serializable;
import java.util.Date;

public class Person implements Cloneable,Serializable{

	private Date time;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	//浅克隆
	protected Object clone() throws CloneNotSupportedException {
		Object obj=super.clone();
		return obj;
	}
	
}
