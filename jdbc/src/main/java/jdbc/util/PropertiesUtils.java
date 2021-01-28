package jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
    public static Properties getDB(){
        InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream("db.properties");
        Properties pro = new Properties();
        try {
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pro;
    }
}
