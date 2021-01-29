package create;

import create.factoryStatic.PersonFactory;
import org.junit.Test;

/**
 * @author LiaoQinZhou
 * @date: 2021/1/29 09:14
 */
public class FactoryStatic {
    @Test
    public void test(){
        //正常创建
        /**
         * 1.动态工厂
         * 2.添加新的车型实现
         * 3.添加新的工厂实现
         */
        System.out.println("-----工厂------");
        new PersonFactory().createMan();
        new PersonFactory().createWoman();
        System.out.println("-----参数------");
        new PersonFactory().create("Man");
        new PersonFactory().create("Woman");
    }
}
