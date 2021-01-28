package myspring.utils;

import myspring.ioc.bean.model.ApplicationXml;
import myspring.ioc.bean.model.BeanDefinition;
import myspring.ioc.bean.model.PropertyDefinition;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 解析xml
 */
public class XmlUtil {

    //解析对应xml文件
    public static ApplicationXml readAppXml(String fileName){
        ApplicationXml xml = new ApplicationXml();
        try {
            //创建读取器
            SAXReader saxReader = new SAXReader();
            Document document=null;
            //获取尧都区的配置文件路径
            URL xmlPath = XmlUtil.class.getClassLoader().getResource(fileName);
            //读取文件
            document = saxReader.read(xmlPath);
            //获取根元素
            Element rootElement = document.getRootElement();
            //校验
            if(!"beans".equals(rootElement.getName())){
                System.err.println(fileName+" format error!");
                return null;
            }
            List<BeanDefinition> beanDefinitions=new ArrayList<>();
            xml.setBeanDefinitionList(beanDefinitions);
            //将内容写入beanDefinitions中
            for(Iterator iterator=rootElement.elementIterator();iterator.hasNext();){
                Element element = (Element) iterator.next();
                String name = element.getName();
                if("component-scan".equals(name)){
                    //扫描目录
                    String value = element.attributeValue("base-package");
                    xml.setComponentScan(value);
                }else if("bean".equals(name)){
                    //添加进beanDefinitions
                    String beanId = element.attributeValue("id");
                    String beanClass = element.attributeValue("class");
                    BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClass);
                    //获取bean的property属性
                    for (Iterator subIterator = element.elementIterator();subIterator.hasNext();){
                        Element subElement = (Element) subIterator.next();
                        String propertyName = subElement.attributeValue("name");
                        String propertyValue = subElement.attributeValue("value");
                        String propertyRef = subElement.attributeValue("ref");
                        PropertyDefinition propertyDefinition = new PropertyDefinition(propertyName, propertyValue, propertyRef);
                        beanDefinition.addPropertyDefinition(propertyDefinition);
                    }
                    beanDefinitions.add(beanDefinition);
                }else{
                    System.out.println("not support tag:"+name);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return xml;
    }
}
