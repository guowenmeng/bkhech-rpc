package com.bkhech.provider;

import com.bkhech.protocol.protocol.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author guowm
 * @date 2021/9/18
 */
@ComponentScan(basePackages = {"com.bkhech.protocol.spring", "com.bkhech.provider"})
@SpringBootApplication
public class NettyRpcProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(NettyRpcProviderApplication.class, args);
        new NettyServer("127.0.0.1", 8080).startNettyServer();
    }
}
