package myspring.ioc.context;

import myspring.mvc.annotation.Autowired;
import myspring.mvc.annotation.Controller;
import myspring.mvc.annotation.RequestMapping;
import myspring.ioc.bean.model.ApplicationXml;
import myspring.ioc.bean.model.BeanDefinition;
import myspring.ioc.bean.impl.DefaultBeanFactory;
import myspring.ioc.bean.model.PropertyDefinition;
import myspring.utils.CommonUtil;
import myspring.utils.XmlUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ApplicationContext的抽象类
 * 具体实现类有ClassPathXml和Web等
 */
public abstract class AbstractApplicationContext implements ApplicationContext{
    //默认配置文件名
    protected String DefaultConfigName="applicationContext.xml";
    //url与方法绑定
    private Map<String, Method> handlerMap =new ConcurrentHashMap<>();
    //url与beanId绑定
    private Map<String,String> controllerMap=new ConcurrentHashMap<>();
    //使用的bean工厂
    private DefaultBeanFactory beanFactory=new DefaultBeanFactory();

    @Override
    public Method getHandlerMethod(String url) {
        return handlerMap.get(url);
    }

    @Override
    public Object getController(String url) {
        if(beanFactory.getBeanClassMap() != null){
            return beanFactory.getBeanClassMap().get(controllerMap.get(url));
        }
        return null;
    }

    @Override
    public Object getBean(String name) {
        return beanFactory.getBean(name);
    }

    /**
     * 在spring源码中，refresh方法做了以下事情：
     * 1、刷新上下文；
     * 2、初始化BeanFactory，获取bean工厂，这里会解析XML文件；
     * 3、对BeanFactory的各种功能进行填充，如注解；
     * 4、激活各种BeanFactory处理器；
     * 5、注册拦截Bean创建的Bean处理器；
     * 6、初始化上下文中的资源文件；
     * 7、初始化上下文事件广播器；
     * 8、给子类扩展初始化其他Bean；
     * 9、在所有bean中查找listener bean并注册；
     * 10、实例化所有非惰性加载的bean；
     * 11、结束：广播事件，这里面对url做了映射。
     *
     * 我们简化处理重要的步骤：解析XML，注册bean，实例化bean，保存URL映射关系
     * @throws Exception
     */
    @Override
    public void refresh() {
        try{
            //1.解析xml
            ApplicationXml xml = XmlUtil.readAppXml(this.DefaultConfigName);
            if(xml != null) {
                List<BeanDefinition> beanDefinitions = xml.getBeanDefinitionList();
                //2.扫描文件夹下的类，并添加到beanDefinitions中
                doScanner(xml.getComponentScan(), beanDefinitions);
                //3.添加beanDefinitions到beanFactory中
                beanFactory.addBeanDefinitions(beanDefinitions);
                //4.执行实例化
                beanFactory.instanceBeans();
                //5.执行属性注入
                beanFactory.injectProperty();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 扫描目录下的文件生成bean
     * @param packageName
     * @param beanDefinitions
     */
    private void doScanner(String packageName, List<BeanDefinition> beanDefinitions) {
        if(CommonUtil.isEmpty(packageName)){
            return;
        }
        //System.out.println(this.getClass().getClassLoader().getResource("/"));
        //System.out.println(this.getClass().getClassLoader().getResource(""));
        URL url = this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
        File dir=new File(url.getFile());
        for(File file:dir.listFiles()){
            if(file.isDirectory()){
                doScanner(packageName+"."+file.getName(),beanDefinitions);
            }else{
                String className = packageName + "." + file.getName().replace(".class", "");
                //扫描class
                scanAnnotation(className,beanDefinitions);
            }
        }
    }

    /**
     * 扫描class下的注解生成bean
     * @param className
     * @param beanDefinitions
     */
    private void scanAnnotation(String className, List<BeanDefinition> beanDefinitions) {
        try{
            //根据截取class名当作id
            String beanId = className.substring(className.lastIndexOf(".") + 1);
            Class<?> clz = Class.forName(className);
            //解析controller注解
            if(clz.isAnnotationPresent(Controller.class)){
                String baseUrl="";
                //查询是否有requestMapping
                if(clz.isAnnotationPresent(RequestMapping.class)){
                    baseUrl = clz.getAnnotation(RequestMapping.class).value();
                }
                //查找方法上的url
                Method[] methods=clz.getMethods();
                for (Method method:methods){
                    //查找requestMapping
                    if(method.isAnnotationPresent(RequestMapping.class)){
                        String methodUrl = method.getAnnotation(RequestMapping.class).value();
                        methodUrl = (baseUrl + "/" + methodUrl).replaceAll("/+","/");
                        handlerMap.put(methodUrl,method);
                        controllerMap.put(methodUrl,beanId);
                    }
                }
            }

            BeanDefinition beanDefinition = new BeanDefinition(beanId, className);
            beanDefinitions.add(beanDefinition);
            //解析autowired
            Field[] fields = clz.getDeclaredFields();
            for (Field field:fields){
                if(field.isAnnotationPresent(Autowired.class)){
                    PropertyDefinition propertyDefinition = new PropertyDefinition(field.getName(), field.getName(), null);
                    beanDefinition.addPropertyDefinition(propertyDefinition);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
