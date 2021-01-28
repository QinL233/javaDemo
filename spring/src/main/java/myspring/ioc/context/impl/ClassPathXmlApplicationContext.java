package myspring.ioc.context.impl;

import myspring.ioc.context.AbstractApplicationContext;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    public ClassPathXmlApplicationContext(String xml) {
        super.DefaultConfigName=xml;
        super.refresh();
    }
}
