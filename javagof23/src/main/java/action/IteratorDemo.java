package action;


import java.util.Objects;

/**
 * 迭代器模式：将方法/对象通过迭代器进行遍历
 * @author LiaoQinZhou
 * @date: 2021/2/26 11:20
 */
public class IteratorDemo {
    public static void main(String[] args) {
        IteratorHandler handler = new IteratorHandler("aaa",222,'c') {
            @Override
            public Object first() {
                return getObjects()[0];
            }

            @Override
            public Object next() {
                Object o = getObjects()[getIndex()];
                increaseIndex();
                return o;
            }

            @Override
            public boolean hasNext() {
                return getObjects().length >= getIndex();
            }
        };
        System.out.println(handler.get());
        System.out.println(handler.get());
        System.out.println(handler.get());
        System.out.println(handler.first());
    }
}

/**
 * 1.迭代器
 */
interface Iterator{
    Object first();
    Object next();
    boolean hasNext();
}

/**
 * 2.处理器，用于赋值，并且实现迭代器
 */
abstract class IteratorHandler implements Iterator{

    private int index = 0;

    private Object[] objects;

    public IteratorHandler(Object... objects) {
        this.objects = objects;
    }

    public int getIndex() {
        return index;
    }

    public void increaseIndex(){
        index++;
    }

    public Object[] getObjects() {
        return objects;
    }

    public Object get(){
        if(hasNext()){
            return next();
        }
        return null;
    }
}