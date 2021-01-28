package myspring.ioc.context.impl;

import myspring.ioc.context.AbstractApplicationContext;

public class WebApplicationContext extends AbstractApplicationContext {
    public WebApplicationContext() {
    }
    public WebApplicationContext(String xml){
        super.DefaultConfigName=xml;
        super.refresh();
    }
}
