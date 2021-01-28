package struct.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

public class Demo {
    public static void main(String[] args){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Someone.class);
        enhancer.setCallback(new ProxyPerson());
        Someone someone = (Someone) enhancer.create();
        someone.action1();
        someone.action2();
        someone.action3();
    }
}
