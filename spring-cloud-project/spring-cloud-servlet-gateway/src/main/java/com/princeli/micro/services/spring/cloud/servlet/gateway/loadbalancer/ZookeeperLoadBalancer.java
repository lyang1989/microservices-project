package com.princeli.micro.services.spring.cloud.servlet.gateway.loadbalancer;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-14 15:03
 **/
public class ZookeeperLoadBalancer extends BaseLoadBalancer {

    private DiscoveryClient discoveryClient;

   private Map<String,BaseLoadBalancer> loadBalancerMap = new ConcurrentHashMap<>();

    public ZookeeperLoadBalancer(DiscoveryClient discoveryClient){
        this.discoveryClient = discoveryClient;

        updateServers();
    }

    @Override
    public Server chooseServer(Object key) {
        if (key instanceof String){
            String serviceName = String.valueOf(key);
            BaseLoadBalancer baseLoadBalancer = loadBalancerMap.get(serviceName);
            return baseLoadBalancer.chooseServer(serviceName);
        }
        return super.chooseServer(key);
    }

    @Scheduled(fixedRate = 5000)
    public void updateServers(){
        discoveryClient.getServices().forEach(serviceName ->{
            BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
           loadBalancerMap.put(serviceName,loadBalancer);
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
            serviceInstances.forEach(serviceInstance -> {
                Server server = new Server(serviceInstance.isSecure()?"https://":"http://",serviceInstance.getHost(),serviceInstance.getPort());
                  loadBalancer.addServer(server);
            });
        });
    }

}
