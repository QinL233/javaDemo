package myspring.ioc.bean.model;

import java.util.ArrayList;
import java.util.List;

/**
 * bean定义
 */
public class BeanDefinition {

    private String id;

    private String className;

    private List<PropertyDefinition> propertyDefinitionList=new ArrayList<>();

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<PropertyDefinition> getPropertyDefinitionList() {
        return propertyDefinitionList;
    }

    public void setPropertyDefinitionList(List<PropertyDefinition> propertyDefinitionList) {
        this.propertyDefinitionList = propertyDefinitionList;
    }

    public void addPropertyDefinition(PropertyDefinition propertyDefinition){
        if(propertyDefinition != null){
            propertyDefinitionList.add(propertyDefinition);
        }
    }
}
