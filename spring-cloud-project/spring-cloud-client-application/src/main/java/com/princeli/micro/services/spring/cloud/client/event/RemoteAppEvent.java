package com.princeli.micro.services.spring.cloud.client.event;

import org.springframework.context.ApplicationEvent;


/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-28 09:20
 **/
public class RemoteAppEvent extends ApplicationEvent{

    /**
     * 事件传输类型
     * HTTP，RPC,MQ
     */
    private String type;
    /**
     * 应用名称
     */
    private String appName;

    /**
     * 是否是集群
     */
    private boolean isCluster;


    /**
     *  @param source pojo事件，json格式
     * @param appName
     * @param isCluster
     */
    public RemoteAppEvent(Object source, String appName, boolean isCluster) {
        super(source);
        this.appName = appName;
        this.isCluster = isCluster;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }


}
