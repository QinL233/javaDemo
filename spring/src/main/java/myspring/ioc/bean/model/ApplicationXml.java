package myspring.ioc.bean.model;

import myspring.ioc.bean.model.BeanDefinition;

import java.util.List;

/**
 * bean的容器
 */
public class ApplicationXml {

    //component节点
    private String componentScan;

    //bean节点
    private List<BeanDefinition> beanDefinitionList;

    //import等其他节点自己实现


    public String getComponentScan() {
        return componentScan;
    }

    public void setComponentScan(String componentScan) {
        this.componentScan = componentScan;
    }

    public List<BeanDefinition> getBeanDefinitionList() {
        return beanDefinitionList;
    }

    public void setBeanDefinitionList(List<BeanDefinition> beanDefinitionList) {
        this.beanDefinitionList = beanDefinitionList;
    }
}
