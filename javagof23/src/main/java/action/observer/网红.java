package action.observer;
public class 网红 extends 目标 {

	@Override
	public void 通知() {
		for (观察者 a : list) {
			a.反应();
		}
	}
}
