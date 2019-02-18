package com.princeli.micro.services.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-02-14 15:58
 **/
@Controller
public class IndexController {

    @GetMapping({"/",""})
    public String index(Model model){
       // model.addAttribute("message","hello word");
        model.addAttribute("string",new StringUtil());
        return "index";
    }


    public static class StringUtil{
        public StringUtil(){

        }

        public boolean isNotBlank(String value){
            return StringUtils.hasText(value);
        }
    }


    @ModelAttribute(name = "message")
    public String message(){
        return "hello word";
    }

}

