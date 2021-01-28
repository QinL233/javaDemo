package action.memento;

public class 备忘录 {

	private String state;

	
	public 备忘录(String state) {
		super();
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
