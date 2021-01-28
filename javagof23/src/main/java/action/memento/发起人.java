package action.memento;

public class 发起人 {

	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public 备忘录 create() {
		return new 备忘录(state);
	}
	
	public void record(备忘录 m) {
		this.setState(m.getState());
	}
}
