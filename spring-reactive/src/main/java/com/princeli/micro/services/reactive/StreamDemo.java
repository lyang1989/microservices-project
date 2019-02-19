package com.princeli.micro.services.reactive;

import java.util.stream.Stream;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-02-19 11:18
 **/
public class StreamDemo {
    public static void main(String[] args) {
        Stream.of(0,1,2,3,4,5,6,7,8,9)
                .filter(v ->v%2 == 1)  //获取奇数
                .map(v -> v-1)         //奇数变偶数
                .reduce(Integer::sum)  //聚合操作
                .ifPresent(System.out::println); //输出结果
                //.forEach(System.out::println);
    }
}
