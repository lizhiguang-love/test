package com.example.demo;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.registry.NacosServiceRegistry;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;

public class Util extends NacosServiceRegistry {


    public Util(NacosDiscoveryProperties nacosDiscoveryProperties) {
        super(nacosDiscoveryProperties);
    }

    @Override
    public void register(Registration registration) {
        System.out.println("id----->"+registration.getInstanceId());
        super.register(registration);
    }
}
