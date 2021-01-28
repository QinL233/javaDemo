package struct.proxy.cglib;

public class Someone {
    private String name;
    public Someone(){

    }
    public Someone(String name){
        this.name=name;
    }

    public void action1() {
        System.out.println(name+"执行任务1");
    }

    public void action2() {
        System.out.println(name+"执行任务2");
    }

    public void action3() {
        System.out.println(name+"执行任务3");
    }
}
