package myspring.ioc.bean.model;

/**
 * bean属性
 */
public class PropertyDefinition {
    private String name;
    private String value;
    //引用类型
    private String ref;

    public PropertyDefinition(String name, String value, String ref) {
        this.name = name;
        this.value = value;
        this.ref = ref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
