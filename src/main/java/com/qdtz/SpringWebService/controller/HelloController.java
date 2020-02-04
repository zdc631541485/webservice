package com.qdtz.SpringWebService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qdtz.SpringWebService.service.TestOracleService;

@Controller
public class HelloController {
    
    @Autowired
    private TestOracleService testOracleService;
    
    @ResponseBody
    @GetMapping("/hello")
    public String hello(){
        return "hello spring boot";
    }
    
    @GetMapping("/toTestHtml")
    public String testHtml(){
        return "test";
    }
    
    @ResponseBody
    @GetMapping(value="/userCount")
    public Integer getUserCount() {
        
        return testOracleService.getSlaveUserCount();
    }
    
    @ResponseBody
    @GetMapping(value="/slaveUserCount")
    public Integer getSlaveUserCount() {
        
        return testOracleService.getUserCount();
    }
    
    @ResponseBody
    @GetMapping(value="/updateUserLastLogin")
    public Integer updateUserLastLogin() {
        
        return testOracleService.updateUserLastLogin();
    }
    
    @ResponseBody
    @GetMapping(value="/updateSlaveUserLastLogin")
    public Integer updateSlaveUserLastLogin() {
        
        return testOracleService.updateSlaveUserLastLogin();
    }
}
