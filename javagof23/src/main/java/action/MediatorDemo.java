package action;

import java.util.ArrayList;
import java.util.List;

/**
 * 中介者模式：将[方法/对象]通过mediatorHandler(中介)进行管理，实现定制中介处理而无需变更对象
 *
 * @author LiaoQinZhou
 * @date: 2021/2/26 11:45
 */
public class MediatorDemo {
    public static void main(String[] args) {
        //构建中介者
        MediatorHandler handler = new MediatorHandler() {
            @Override
            void start(Mediator mediator, String msg) {
                this.mediators.forEach(c -> {
                    if (!c.equals(mediator)) {
                        c.receive(msg);
                    }
                });
            }
        };

        //构建具体的同事类
        Mediator mediator1 = new Mediator(handler) {
            @Override
            void receive(String msg) {
                System.out.println("mediator1 receive:"+msg);
            }
        };
        Mediator mediator2 = new Mediator(handler) {
            @Override
            void receive(String msg) {
                System.out.println("mediator2 receive:"+msg);
            }
        };
        Mediator mediator3 = new Mediator(handler) {
            @Override
            void receive(String msg) {
                System.out.println("mediator3 receive:"+msg);
            }
        };

        //发送消息
        mediator1.send("你好呀，我是mediator1");
        mediator2.send("你好呀，我是mediator2");
    }
}

/**
 * 1.将方法定制为一段mediator
 * mediator必须请中介(handler)
 * 数据通过send/receive收发
 */
abstract class Mediator {

    public MediatorHandler handler = null;

    public Mediator(MediatorHandler handler) {
        this.handler = handler;
        handler.register(this);
    }

    public void send(String msg){
        handler.start(this,msg);
    }

    abstract void receive(String msg);
}

/**
 * 2.中介者处理器，对所有代理人进行处理
 * 处理器需要记录mediator(不安全)
 * start可定制
 */
abstract class MediatorHandler {

    public List<Mediator> mediators = new ArrayList<>();

    public void register(Mediator mediator){
        mediators.add(mediator);
    }

    abstract void start(Mediator mediator,String msg);
}