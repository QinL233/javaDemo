package com.lqz;

import com.sun.jndi.rmi.registry.RemoteReference;

import javax.naming.NamingException;
import javax.naming.Reference;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * @author liaoqinzhou_sz
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021年12月13日 14:57:00
 */
public class TestService implements RemoteReference, Serializable {

    public Reference getReference() throws NamingException, RemoteException {
        System.out.println(111);
        Reference test = new Reference("test");
        return test;
    }
}
