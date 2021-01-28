package myspring.mvc.servlet.model;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    //id
    private String name;
    //模型
    private Map<String,Object> attributes=new HashMap<>();

    public ModelAndView() {
    }

    public ModelAndView(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public void addModelAndView(String key,Object value){
        this.attributes.put(key,value);
    }
}
