package myspring.aop.proxy.service.impl;

import myspring.aop.proxy.service.Service;

public class ServiceImpl implements Service {
    @Override
    public void method1() {
        System.out.println("method1");
    }

    @Override
    public void method2(String msg) {
        System.out.println(msg);
    }
}
