package com.pocsecurity.gatewaiinterface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class GatewaiInterfaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewaiInterfaceApplication.class, args);
    }

}
