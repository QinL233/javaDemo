package myspring.utils;

import java.util.Collection;
import java.util.Map;

public class CommonUtil {

    /**默认的JSP文件路径前缀，实际应该在application xml配置*/
    public static final String JSP_PATH_PREFIX = "/WEB-INF/view/";

    public static boolean isEmpty(String string){
        return string ==null || string.trim().length() == 0;
    }

    public static boolean isEmpty(Collection<?> collection){
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?,?> map){
        return map == null || map.isEmpty();
    }

    public static Object convert(String value,Class<?> type){
        Object result=null;
        try {
            if (Integer.TYPE.equals(type) || Integer.class.equals(type)) {
                result = Integer.parseInt(value);
            } else if (Long.TYPE.equals(type) || Long.TYPE.equals(type)) {
                result = Long.parseLong(value);
            } else if (Short.TYPE.equals(type) || Short.class.equals(type)) {
                result = Short.parseShort(value);
            } else if (Double.TYPE.equals(type) || Double.class.equals(type)) {
                result = Double.parseDouble(value);
            } else if (Float.TYPE.equals(type) || Float.class.equals(type)) {
                result = Float.parseFloat(value);
            } else if (Boolean.TYPE.equals(type) || Boolean.class.equals(type)) {
                result = Boolean.parseBoolean(value);
            } else {
                //其它类型抛出
                result = type.cast(value);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
