package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2020/6/12
 * Time: 下午 03:07
 * Description:
 */
@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
