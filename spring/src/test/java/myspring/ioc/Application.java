package myspring.ioc;

import myspring.ioc.context.ApplicationContext;
import myspring.ioc.context.impl.ClassPathXmlApplicationContext;
import myspring.ioc.service.UserService;

public class Application {
    public static void main(String[] args){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = (UserService)context.getBean("userService");
        System.out.println(userService.selectById(123));
        System.out.println(userService.selectById(12));
    }
}
