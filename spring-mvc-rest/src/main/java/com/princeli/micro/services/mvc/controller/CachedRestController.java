package com.princeli.micro.services.mvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-02-15 13:49
 **/
@RestController
public class CachedRestController {

    @RequestMapping
    @ResponseBody
    //http协议
    public String helloWorld(){
        return "Hello,World";
    }


    @RequestMapping("/cache")
    public ResponseEntity<String> cacheHelloWorld(
            @RequestParam(required = false,defaultValue = "false") boolean cached){

        if (cached){
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }else{
            return ResponseEntity.ok("hello,World");
        }
//
//        ResponseEntity<String> entity = new ResponseEntity<>("hello,World", HttpStatus.NOT_MODIFIED);
//        return entity;
    }
}
