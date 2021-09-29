package com.study.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 */

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "Hello";
    }
    @GetMapping("/employee/basic/hello")
    public String hello1(){
        return "Hello";
    }
    @GetMapping("/employee/advanced/hello")
    public String hello2(){
        return "Hello";
    }
}
