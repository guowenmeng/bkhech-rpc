package com.bkhech.consumer;

import com.bkhech.api.IUserService;
import com.bkhech.consumer.proxy.RpcClientProxy;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        RpcClientProxy clientProxy = new RpcClientProxy();
        IUserService userService = clientProxy.createProxy(IUserService.class, "127.0.0.1", 8080);
        System.out.println(userService.saveUser("bkhech"));
    }
}
