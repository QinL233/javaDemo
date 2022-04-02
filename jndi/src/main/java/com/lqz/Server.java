package com.lqz;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * @author liaoqinzhou_sz
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021年12月13日 14:20:00
 */
public class Server {
    public static void main(String[] args) throws Exception {
        LocateRegistry.createRegistry(8888);
        Naming.rebind("//172.17.129.17:8888/test", new TestService());
        System.out.println("server start....");
        Thread.sleep(1000000 * 1000);
        System.out.println("server end....");
    }
}


