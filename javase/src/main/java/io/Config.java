package io;

import java.io.File;

public class Config {
    public static final String utf8="utf8";
    public static final String host="127.0.0.1";
    public static final Integer port=9090;
    public static final Integer port2=9091;
    public static final String classesPath = Config.class.getClassLoader().getResource("").toString();
    public static final String resources = System.getProperty("user.dir")+File.separator+"src\\main\\resources\\";
    public static final String fileName ="file.txt";
    public static final String fileName2="file2.txt";
    public static final String file=resources+File.separator+fileName;
    public static final String file2=resources+File.separator+fileName2;

}
