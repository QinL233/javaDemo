package action;

/**
 * 解析器模式：将[方法对象]通过interpreterHandler进行解析
 * @author LiaoQinZhou
 * @date: 2021/2/25 11:15
 */
public class InterpreterDemo {
    public static void main(String[] args) {
        InterpreterHandler handler = new InterpreterHandler() {
            @Override
            public void execute(String s) {
                System.out.println(s.equals("admin"));
            }
        };
        handler.start("aaa");
        handler.start("admin");
    }
}

/**
 * 1.解析者
 */
interface Interpreter{
    void execute(String s);
}

/**
 * 2.处理器
 */
abstract class InterpreterHandler implements Interpreter{
    public void start(String s){
        execute(s);
    }
}
